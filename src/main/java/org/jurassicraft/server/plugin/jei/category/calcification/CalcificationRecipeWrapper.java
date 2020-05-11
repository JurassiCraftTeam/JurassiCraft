package org.jurassicraft.server.plugin.jei.category.calcification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.genetics.DinoDNA;
import org.jurassicraft.server.genetics.GeneticsHelper;
import org.jurassicraft.server.item.ItemHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CalcificationRecipeWrapper implements IRecipeWrapper {
    private final CalcificationInput input;

    public CalcificationRecipeWrapper(CalcificationInput input) {
        this.input = input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new ArrayList<>();

        int metadata = EntityHandler.getDinosaurId(this.input.dinosaur);
        NBTTagCompound tag = new NBTTagCompound();
        DinoDNA dna = new DinoDNA(this.input.dinosaur, 100, GeneticsHelper.randomGenetics(new Random()));
        dna.writeToNBT(tag);

        ItemStack inputStack = new ItemStack(ItemHandler.SYRINGE, 1, metadata);
        inputStack.setTagCompound(tag);

        inputs.add(inputStack);
        
        inputs.add(new ItemStack(Items.EGG));
        
        ingredients.setInputs(ItemStack.class, inputs);

        ItemStack outputStack = new ItemStack(ItemHandler.EGG, 1, metadata);
        outputStack.setTagCompound(tag);
        ingredients.setOutput(ItemStack.class, outputStack);
    }


    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
