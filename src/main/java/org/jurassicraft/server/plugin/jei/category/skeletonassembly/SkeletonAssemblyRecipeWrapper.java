package org.jurassicraft.server.plugin.jei.category.skeletonassembly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.DisplayBlockItem;
import org.jurassicraft.server.item.FossilItem;
import org.jurassicraft.server.item.ItemHandler;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class SkeletonAssemblyRecipeWrapper implements IRecipeWrapper {
    private final SkeletonInput input;

    public SkeletonAssemblyRecipeWrapper(SkeletonInput input) {
        this.input = input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
    	String[][] recipe = this.input.dinosaur.getMetadata().getRecipe();
        Map<String, FossilItem> fossils = this.input.fresh ? ItemHandler.FRESH_FOSSILS : ItemHandler.FOSSILS;
        int id = EntityHandler.getDinosaurId(this.input.dinosaur);

        List<ItemStack> inputs = new ArrayList<>(recipe.length);
        for (String[] row : recipe) {
            for (int i = 0; i < 5; i++) {
                String column = i < row.length ? row[i] : "";
                if (!column.isEmpty()) {
                    inputs.add(new ItemStack(fossils.get(column), 1, id));
                } else {
                    inputs.add(null);
                }
            }
        }
        ingredients.setInputs(ItemStack.class, inputs);

        ItemStack output = new ItemStack(ItemHandler.DISPLAY_BLOCK_ITEM, 1, DisplayBlockItem.getMetadata(id, !this.input.fresh, true));
        ingredients.setOutput(ItemStack.class, output);
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
