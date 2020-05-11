package org.jurassicraft.server.item;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jurassicraft.client.event.ClientEventHandler;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.tab.TabHandler;
import org.jurassicraft.server.util.LangUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DinosaurSpawnEggItem extends Item {
    public DinosaurSpawnEggItem() {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(TabHandler.EGGS);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return true;
    }

    public static DinosaurEntity spawnDinosaur(World world, EntityPlayer player, ItemStack stack, double x, double y, double z) {
        Dinosaur dinosaur = getDinosaur(stack);
        if (dinosaur != null) {
            try {
            	DinosaurEntity entity = dinosaur.construct(world);
                entity.setDNAQuality(100);

                int mode = getMode(stack);
                if (mode > 0) {
                    entity.setMale(mode == 1);
                }

                if (player.isSneaking()) {
                    entity.setAge(0);
                }

                entity.setPosition(x, y, z);
                entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                entity.rotationYawHead = entity.rotationYaw;
                entity.renderYawOffset = entity.rotationYaw;
                return entity;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    	ItemStack stack = player.getHeldItem(hand);
    	if(player.isSneaking()) {
            int mode = changeMode(stack);
            if (world.isRemote) {
            	TextComponentString change = new TextComponentString(LangUtils.translate(LangUtils.GENDER_CHANGE.get("spawnegg")).replace("{mode}", LangUtils.getGenderMode(mode)));
				change.getStyle().setColor(TextFormatting.GOLD);
				ClientProxy.MC.ingameGUI.addChatMessage(ChatType.GAME_INFO, change);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return LangUtils.translate(this.getUnlocalizedName() + ".name").replace("{dino}", LangUtils.getDinoName(getDinosaur(stack)));
    }

    public static Dinosaur getDinosaur(ItemStack stack) {
        Dinosaur dinosaur = EntityHandler.getDinosaurById(stack.getItemDamage());

        if (dinosaur == null) {
            dinosaur = EntityHandler.VELOCIRAPTOR;
        }

        return dinosaur;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subtypes) {
        List<Dinosaur> dinosaurs = new LinkedList<>(EntityHandler.getDinosaurs().values());

        Collections.sort(dinosaurs);
        if(this.isInCreativeTab(tab))
        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur.shouldRegister()) {
                subtypes.add(new ItemStack(this, 1, EntityHandler.getDinosaurId(dinosaur)));
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    	ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
            return EnumActionResult.PASS;
        } else {
            IBlockState state = world.getBlockState(pos);

            if (state.getBlock() == Blocks.MOB_SPAWNER) {
                TileEntity tile = world.getTileEntity(pos);

                if (tile instanceof TileEntityMobSpawner) {
                    MobSpawnerBaseLogic spawnerLogic = ((TileEntityMobSpawner) tile).getSpawnerBaseLogic();
                    spawnerLogic.setEntityId(EntityList.getKey(getDinosaur(stack).getMetadata().getDinosaurClass()));
                    tile.markDirty();

                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }

            pos = pos.offset(side);
            double yOffset = 0.0D;

            if (side == EnumFacing.UP && state.getBlock() instanceof BlockFence) {
                yOffset = 0.5D;
            }

            DinosaurEntity dinosaur = spawnDinosaur(world, player, stack, pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);

            if (dinosaur != null) {
                if (stack.hasDisplayName()) {
                    dinosaur.setCustomNameTag(stack.getDisplayName());
                }

                if (!player.capabilities.isCreativeMode) {
                    stack.shrink(1);
                }

                world.spawnEntity(dinosaur);
                dinosaur.playLivingSound();
            }

            return EnumActionResult.SUCCESS;
        }
    }

    public static int getMode(ItemStack stack) {
        return getNBT(stack).getInteger("GenderMode");
    }

    public static int changeMode(ItemStack stack) {
        NBTTagCompound nbt = getNBT(stack);

        int mode = getMode(stack) + 1;
        mode %= 3;

        nbt.setInteger("GenderMode", mode);

        stack.setTagCompound(nbt);

        return mode;
    }

    public static NBTTagCompound getNBT(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }
        stack.setTagCompound(nbt);
        return nbt;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> lore, ITooltipFlag tooltipFlag) {
    	int mode = getMode(stack);
    	Boolean type = (mode > 0 ? mode == 1 : null);
    	lore.add(TextFormatting.GOLD + LangUtils.translate("gender.name") +": "+ LangUtils.getGenderMode(type != null ? (type == true ? 1 : 2) : 0));
    	lore.add(TextFormatting.WHITE + I18n.format("lore.change_gender2.name"));
    	lore.add(TextFormatting.YELLOW + I18n.format("lore.baby_dino.name"));
        
    }
}
