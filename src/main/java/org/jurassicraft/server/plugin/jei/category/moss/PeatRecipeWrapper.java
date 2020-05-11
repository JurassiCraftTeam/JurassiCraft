package org.jurassicraft.server.plugin.jei.category.moss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.item.ItemHandler;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PeatRecipeWrapper implements IRecipeWrapper {
    private final PeatInput input;

    public PeatRecipeWrapper(PeatInput input) {
        this.input = input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInputs(ItemStack.class, new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Item.getItemFromBlock(BlockHandler.PEAT)), new ItemStack(Item.getItemFromBlock(BlockHandler.MOSS)))));
        ingredients.setOutput(ItemStack.class, new ItemStack(Item.getItemFromBlock(BlockHandler.MOSS)));
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
