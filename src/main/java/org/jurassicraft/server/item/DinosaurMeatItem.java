package org.jurassicraft.server.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.tuple.Pair;
import org.jurassicraft.server.api.ExtractableItem;
import org.jurassicraft.server.api.SequencableItem;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.genetics.DinoDNA;
import org.jurassicraft.server.genetics.GeneticsHelper;
import org.jurassicraft.server.tab.TabHandler;
import org.jurassicraft.server.util.LangUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DinosaurMeatItem extends ItemFood implements ExtractableItem {
    public DinosaurMeatItem() {
        super(3, 0.3F, true);
        this.setHasSubtypes(true);

        this.setCreativeTab(TabHandler.FOODS);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return LangUtils.translate(this.getUnlocalizedName() + ".name").replace("{dino}", LangUtils.getDinoName(getDinosaur(stack)));
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            getDinosaur(stack).applyMeatEffect(player, false);
        }
    }

    public static Dinosaur getDinosaur(ItemStack stack) {
        return EntityHandler.getDinosaurById(stack.getItemDamage());
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
    public void addInformation(ItemStack stack, World worldIn, List<String> lore, ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt != null && nbt.hasKey("Genetics") && nbt.hasKey("DNAQuality")) {
            int quality = nbt.getInteger("DNAQuality");

            TextFormatting colour;

            if (quality > 75) {
                colour = TextFormatting.GREEN;
            } else if (quality > 50) {
                colour = TextFormatting.YELLOW;
            } else if (quality > 25) {
                colour = TextFormatting.GOLD;
            } else {
                colour = TextFormatting.RED;
            }


            lore.add(colour + LangUtils.translate(LangUtils.LORE.get("dna_quality")).replace("{quality}", LangUtils.getFormattedQuality(quality)));
            lore.add(TextFormatting.BLUE + LangUtils.translate(LangUtils.LORE.get("genetic_code")).replace("{code}", LangUtils.getFormattedGenetics(nbt.getString("Genetics"))));
        }
    }
    
    @Override
	public List<ItemStack> getJEIRecipeTypes() {
		List<ItemStack> list = this.getItemSubtypes(this);
		return list;
	}

	@Override
	public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
		return null;
	}

	@Override
	public ItemStack getExtractOutput(ItemStack stack, Random random) {
		NBTTagCompound nbt = stack.getTagCompound();

        if (nbt == null) {
            nbt = new NBTTagCompound();
            DinoDNA dna = new DinoDNA(EntityHandler.getDinosaurById(stack.getItemDamage()), -1, "");
            dna.writeToNBT(nbt);
        } else if (!nbt.hasKey("Dinosaur")) {
            nbt.setInteger("Dinosaur", stack.getItemDamage());
        }

        ItemStack output = new ItemStack(ItemHandler.STORAGE_DISC);
        output.setTagCompound(nbt);

        return output;
	}

    //INFO: use DNAContainerItem.getDNAQuality()
//    public int getDNAQuality(EntityPlayer player, ItemStack stack) {
//        int quality = player.capabilities.isCreativeMode ? 100 : 0;
//
//        NBTTagCompound nbt = stack.getTagCompound();
//
//        if (nbt == null) {
//            nbt = new NBTTagCompound();
//        }
//
//        if (nbt.hasKey("DNAQuality")) {
//            quality = nbt.getInteger("DNAQuality");
//        } else {
//            nbt.setInteger("DNAQuality", quality);
//        }
//
//        stack.setTagCompound(nbt);
//
//        return quality;
//    }

    //INFO: use DNAContainerItem.getGeneticCode()
//    public String getGeneticCode(EntityPlayer player, ItemStack stack) {
//        NBTTagCompound nbt = stack.getTagCompound();
//
//        String genetics = GeneticsHelper.randomGenetics(player.world.rand);
//
//        if (nbt == null) {
//            nbt = new NBTTagCompound();
//        }
//
//        if (nbt.hasKey("Genetics")) {
//            genetics = nbt.getString("Genetics");
//        } else {
//            nbt.setString("Genetics", genetics);
//        }
//
//        stack.setTagCompound(nbt);
//
//        return genetics;
//    }

//    @Override
//    public void addInformation(ItemStack stack, World world, List<String> lore, ITooltipFlag tooltipFlag) {
//        int quality = this.getDNAQuality(player, stack);
//
//        TextFormatting formatting;
//
//        if (quality > 75) {
//            formatting = TextFormatting.GREEN;
//        } else if (quality > 50) {
//            formatting = TextFormatting.YELLOW;
//        } else if (quality > 25) {
//            formatting = TextFormatting.GOLD;
//        } else {
//            formatting = TextFormatting.RED;
//        }
//
//        lore.add(formatting + new LangHelper("lore.dna_quality.name").withProperty("quality", quality + "").build());
//        lore.add(TextFormatting.BLUE + new LangHelper("lore.genetic_code.name").withProperty("code", this.getGeneticCode(player, stack)).build());
//    }
}
