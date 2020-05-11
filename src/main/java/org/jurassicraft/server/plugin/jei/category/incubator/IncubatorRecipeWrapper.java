package org.jurassicraft.server.plugin.jei.category.incubator;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.ItemHandler;

public class IncubatorRecipeWrapper implements IRecipeWrapper {

    private final Dinosaur dinosaur;

    public IncubatorRecipeWrapper(IncubatorInput input) {
        this.dinosaur = input.getDinosaur();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
    	List<ItemStack> inputs = new ArrayList<>();
    	int meta = EntityHandler.getDinosaurId(this.dinosaur);
    	inputs.add(new ItemStack(ItemHandler.EGG, 1, meta));
    	inputs.add(new ItemStack(Item.getItemFromBlock(BlockHandler.PEAT_MOSS), 1));
        ingredients.setInputs(ItemStack.class, inputs);
    }

    public Dinosaur getDinosaur() {
        return dinosaur;
    }
}