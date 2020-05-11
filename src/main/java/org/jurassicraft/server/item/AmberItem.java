package org.jurassicraft.server.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;
import org.jurassicraft.server.api.ExtractableItem;
import org.jurassicraft.server.api.SequencableItem;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.dinosaur.DinosaurMetadata;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.genetics.DinoDNA;
import org.jurassicraft.server.genetics.GeneticsHelper;
import org.jurassicraft.server.genetics.PlantDNA;
import org.jurassicraft.server.tab.TabHandler;
import org.jurassicraft.server.util.LangUtils;

import com.google.common.collect.Lists;

public class AmberItem extends Item implements ExtractableItem {
    public AmberItem() {
        super();
        this.setCreativeTab(TabHandler.ITEMS);
        this.setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return LangUtils.translate(this.getUnlocalizedName() + ".name").replace("{stored}", LangUtils.translate("amber." + (stack.getItemDamage() == 0 ? "mosquito" : "aphid") + ".name"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(this.isInCreativeTab(tab))
        {
            subItems.add(new ItemStack(this, 1, 0));
            subItems.add(new ItemStack(this, 1, 1));
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
        	if(this.getMetadata(stack) == 0) {
        		nbt = new NBTTagCompound();
                DinoDNA dna = new DinoDNA(EntityHandler.getDinosaurById(-1), -1, "");
                dna.writeToNBT(nbt);
        	}else if(this.getMetadata(stack) == 1) {
        		nbt = new NBTTagCompound();
            	PlantDNA dna = new PlantDNA(-1, -1);
            	dna.writeToNBT(nbt);
        	}
        }else if (this.getMetadata(stack) == 0 && !nbt.hasKey("Dinosaur")) {
            nbt.setInteger("Dinosaur", stack.getItemDamage());
        }

        ItemStack output = new ItemStack(ItemHandler.STORAGE_DISC);
        output.setTagCompound(nbt);

        return output;
	}
}
