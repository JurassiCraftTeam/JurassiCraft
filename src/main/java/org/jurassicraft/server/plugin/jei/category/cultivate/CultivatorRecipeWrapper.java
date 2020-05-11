package org.jurassicraft.server.plugin.jei.category.cultivate;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.ItemHandler;

public class CultivatorRecipeWrapper implements IRecipeWrapper {
    private final Dinosaur dinosaur;

    public CultivatorRecipeWrapper(CultivateInput input) {
        this.dinosaur = input.dinosaur;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        int meta = EntityHandler.getDinosaurId(this.dinosaur);
        ingredients.setInput(ItemStack.class, new ItemStack(ItemHandler.SYRINGE, 1, meta));
        ingredients.setOutput(ItemStack.class, new ItemStack(ItemHandler.HATCHED_EGG, 1, meta));
    }

    public Dinosaur getDinosaur() {
        return dinosaur;
    }
}
