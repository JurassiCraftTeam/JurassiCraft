package org.jurassicraft.server.item;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.jurassicraft.server.api.SynthesizableItem;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.genetics.DinoDNA;
import org.jurassicraft.server.genetics.PlantDNA;
import org.jurassicraft.server.genetics.StorageType;
import org.jurassicraft.server.genetics.StorageTypeRegistry;
import org.jurassicraft.server.plant.PlantHandler;
import org.jurassicraft.server.tab.TabHandler;

import java.util.List;
import java.util.Random;

public class StorageDiscItem extends Item implements SynthesizableItem {
    public StorageDiscItem() {
        super();
        this.setCreativeTab(TabHandler.ITEMS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> toolTip, ITooltipFlag flagIn)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            String storageId = tag.getString("StorageId");
            StorageType type = StorageTypeRegistry.getStorageType(storageId);
            if (type != null) {
                type.readFromNBT(tag);
                type.addInformation(stack, toolTip);
            }
        } else {
            toolTip.add(TextFormatting.RED + I18n.format("cage.empty.name"));
        }
    }

    @Override
    public boolean isSynthesizable(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        return tagCompound != null && tagCompound.hasKey("DNAQuality") && tagCompound.getInteger("DNAQuality") == 100;
    }

    @Override
    public ItemStack getSynthesizedItem(ItemStack stack, Random random) {
        NBTTagCompound tag = stack.getTagCompound();
        StorageType type = StorageTypeRegistry.getStorageType(tag.getString("StorageId"));
        type.readFromNBT(tag);
        return type.createItem();
    }

    @Override
    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
        NBTTagCompound tag = inputItem.getTagCompound();
        StorageType type = StorageTypeRegistry.getStorageType(tag.getString("StorageId"));
        type.readFromNBT(tag);
        return Lists.newArrayList(Pair.of(100F, type.createItem()));
    }

    @Override
    public List<ItemStack> getJEIRecipeTypes() {
        List<ItemStack> list = Lists.newArrayList();

        EntityHandler.getRegisteredDinosaurs().forEach(dino -> {
            DinoDNA dna = new DinoDNA(dino, -1, "");
            ItemStack stack = new ItemStack(this);
            NBTTagCompound nbt = new NBTTagCompound();
            dna.writeToNBT(nbt);
            stack.setTagCompound(nbt);
            list.add(stack);
        });

        PlantHandler.getPlants().stream().filter(plant -> plant.isPrehistoric()).forEach(plant -> {
        	
            PlantDNA dna = new PlantDNA(PlantHandler.getPlantId(plant), -1);
            ItemStack stack = new ItemStack(this);
            NBTTagCompound nbt = new NBTTagCompound();
            dna.writeToNBT(nbt);
            stack.setTagCompound(nbt);
            list.add(stack);

        });
        return list;
    }
}
