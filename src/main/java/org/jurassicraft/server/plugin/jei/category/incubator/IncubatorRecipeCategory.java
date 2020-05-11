package org.jurassicraft.server.plugin.jei.category.incubator;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.plugin.jei.JurassiCraftJEIPlugin;

public class IncubatorRecipeCategory implements IRecipeCategory<IncubatorRecipeWrapper> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(JurassiCraft.MODID, "textures/gui/incubator.png");

    private final IDrawable background;
    private final String title;

    public IncubatorRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 31, 13, 112, 53);
        this.title = I18n.translateToLocal("container.incubator");

    }

    @Override
    public String getUid() {
        return JurassiCraftJEIPlugin.INCUBATOR;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IncubatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();
        stackGroup.init(0, true, 47, 0);
        stackGroup.set(0, ingredients.getInputs(ItemStack.class).get(0));
        stackGroup.init(1, true, 47, 35);
        stackGroup.set(1, ingredients.getInputs(ItemStack.class).get(1));
    }

    @Override
    public String getModName() {
        return JurassiCraft.NAME;
    }

}