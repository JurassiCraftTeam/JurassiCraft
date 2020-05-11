package org.jurassicraft.server.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jurassicraft.client.event.ClientEventHandler;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.client.render.RenderingHandler;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.entity.DisplayBlockEntity;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.tab.TabHandler;
import org.jurassicraft.server.util.LangUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class DisplayBlockItem extends Item {
	
    public DisplayBlockItem() {
        super();
        this.setCreativeTab(TabHandler.DECORATIONS);
        this.setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        pos = pos.offset(side);
        ItemStack stack = player.getHeldItem(hand);
        if (!player.world.isRemote && player.canPlayerEdit(pos, side, stack) && !player.isSneaking()) {
            Block block = BlockHandler.DISPLAY_BLOCK;

            if (block.canPlaceBlockAt(world, pos)) {
                IBlockState state = block.getDefaultState();
                world.setBlockState(pos, block.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, 0, player));
                block.onBlockPlacedBy(world, pos, state, player, stack);

                int mode = getGenderType(stack);
                world.playSound(null, pos, SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, (SoundType.WOOD.getVolume() + 1.0F) / 2.0F, SoundType.WOOD.getPitch() * 0.8F);
                DisplayBlockEntity tile = (DisplayBlockEntity) world.getTileEntity(pos);

                if (tile != null) {
                    tile.setDinosaur(getDinosaurID(stack), mode > 0 ? mode == 1 : world.rand.nextBoolean(), isSkeleton(stack), isFossilized(stack), getSkeletonType(stack));
                    tile.setRot(180 - (int) player.getRotationYawHead());
                    world.notifyBlockUpdate(pos, state, state, 0);
                    tile.markDirty();
                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }
                }
            }
        }else if(player.isSneaking()) {
        	int mode = changeMode(stack);
			if (world.isRemote) {
				TextComponentString change = new TextComponentString(LangUtils.translate(LangUtils.GENDER_CHANGE.get("actionfigure")).replace("{mode}", LangUtils.getGenderMode(mode)));
				change.getStyle().setColor(TextFormatting.GOLD);
				ClientProxy.MC.ingameGUI.addChatMessage(ChatType.GAME_INFO, change);
			}
        }

        return EnumActionResult.SUCCESS;
    }
    
    private static Boolean getGender(World world, ItemStack stack){
		Boolean type = null;
		if (world != null) {
			int mode = getGenderType(stack);
			type = (mode > 0 ? mode == 1 : null);
		}
		return type;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String dinoName = LangUtils.getDinoName(getDinosaur(stack));
        if (!isSkeleton(stack)) {
            return LangUtils.translate("item.action_figure.name").replace("{dino}", dinoName);
        }
        return LangUtils.translate("item.skeleton." + (isFossilized(stack) == true ? "fossil" : "fresh") + ".name").replace("{dino}", dinoName);
    }

    public static Dinosaur getDinosaur(ItemStack stack) {
        return EntityHandler.getDinosaurById(getDinosaurID(stack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subtypes) {
        List<Dinosaur> dinosaurs = new LinkedList<>(EntityHandler.getDinosaurs().values());
        Collections.sort(dinosaurs);
        
        if(this.isInCreativeTab(tab))
        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur.shouldRegister()) {
                subtypes.add(new ItemStack(this, 1, getMetadata(EntityHandler.getDinosaurId(dinosaur), false, false)));
                subtypes.add(new ItemStack(this, 1, getMetadata(EntityHandler.getDinosaurId(dinosaur), true, true)));
                subtypes.add(new ItemStack(this, 1, getMetadata(EntityHandler.getDinosaurId(dinosaur), false, true)));
            }
        }
    }

    public static int getMetadata(int dinosaur, boolean isFossilized, boolean isSkeleton) {
    	return dinosaur << 2 | ((isFossilized ? 1 : 0) << 1) | (isSkeleton ? 1 : 0);
    }

    public static int getDinosaurID(ItemStack stack) {
        return stack.getMetadata() >> 2;
    }
    
    public static boolean isFossilized(ItemStack stack) {
    	return (stack.getMetadata() >> 1 & 1) == 1;
    }
    
    public static boolean isSkeleton(ItemStack stack) {
        return (stack.getMetadata() & 1) == 1;
    }
    
    public static byte getGenderType(ItemStack stack) {
    	
    	if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Gender")) {
    		return stack.getTagCompound().getByte("Gender");
    	}else {
    		return 0;
    	}
    }
    
    public static byte getSkeletonType(ItemStack stack) {
    	
    	if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Type")) {
    		return stack.getTagCompound().getByte("Type");
    	}else {
    		return 0;
    	}
    }
    
    public static void setGenderType(ItemStack stack, byte type) {
    	NBTTagCompound nbt = stack.getTagCompound();
    	if(nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
    	}
    	nbt.setByte("Gender", type);
    }
    
    public static void setSkeletonType(ItemStack stack, byte type) {
    	NBTTagCompound nbt = stack.getTagCompound();
    	if(nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
    	}
    	nbt.setByte("Type", type);
    }

    public static int changeMode(ItemStack stack) {
        int mode = getGenderType(stack) + 1;
        mode %= 3;
        setGenderType(stack, (byte) mode);
        return mode;
    }
    
    public static int changeSkeletonVariant(ItemStack stack) {
    	
        int dinosaur = getDinosaurID(stack);
        int newVariant = getSkeletonType(stack) + 1;
        
        newVariant %= 16;
        if(!(newVariant < EntityHandler.getDinosaurById(dinosaur).getMetadata().skeletonPoses().length)){
        	newVariant = 0;
        }
        
        setSkeletonType(stack, (byte) newVariant);
        return newVariant;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> lore, ITooltipFlag tooltipFlag) {
		
		Boolean type = getGender(world, stack);
		lore.add(TextFormatting.GOLD + LangUtils.translate("gender.name") + ": " + LangUtils.getGenderMode(type != null ? (type == true ? 1 : 2) : 0));
		lore.add(TextFormatting.WHITE + LangUtils.translate("lore.change_gender.name"));
		Dinosaur dinosaur = EntityHandler.getDinosaurById(getDinosaurID(stack));
		
		if (isSkeleton(stack) && dinosaur.getMetadata().skeletonPoses().length > 1) {
			lore.add(TextFormatting.YELLOW + LangUtils.translate("pose.name") + ": " + LangUtils.getSkeletonMode(dinosaur, getSkeletonType(stack)));
			lore.add(TextFormatting.WHITE + LangUtils.translate("lore.change_variant.name"));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (isSkeleton(stack)) {
			if (player.isSneaking()) {
				int oldVariant = getSkeletonType(stack);
				int variant = changeSkeletonVariant(stack);
				if (variant != oldVariant && world.isRemote) {
					TextComponentString change = new TextComponentString(LangUtils.translate(LangUtils.SKELETON_CHANGE.get("variant")).replace("{mode}", LangUtils.getSkeletonMode(EntityHandler.getDinosaurById(getDinosaurID(stack)), variant)));
					change.getStyle().setColor(TextFormatting.YELLOW);
					ClientProxy.MC.ingameGUI.addChatMessage(ChatType.GAME_INFO, change);
				}
			}
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
