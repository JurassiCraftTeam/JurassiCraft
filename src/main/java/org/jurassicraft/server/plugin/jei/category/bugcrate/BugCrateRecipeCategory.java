package org.jurassicraft.server.plugin.jei.category.bugcrate;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import java.util.List;

@SideOnly(Side.CLIENT)
public class BugCrateRecipeCategory implements IRecipeCategory<BugCrateRecipeWrapper> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(JurassiCraft.MODID, "textures/gui/bug_crate.png");

    private final IDrawable background;
    private final String title;
    private final IDrawableAnimated arrow;

    public BugCrateRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 25, 16, 118, 54);
        this.title = BlockHandler.BUG_CRATE.getLocalizedName();

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(TEXTURE, 177, 0, 23, 16);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.arrow.draw(minecraft, 64, 19);
    }

    @Override
    public String getUid() {
        return "jurassicraft.bug_crate";
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
    public void setRecipe(IRecipeLayout recipeLayout, BugCrateRecipeWrapper recipeWrapper, IIngredients ingredients) {
    	IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

        stackGroup.init(0, true, 0, 0);
        stackGroup.set(0, inputs.get(0));
        
        stackGroup.init(1, true, 0, 34);
        stackGroup.set(1, inputs.get(1));
        
        
        stackGroup.init(2, false, 100, 0);
        stackGroup.set(2, outputs.get(0));
    }

	@Override
	public String getModName() {
	    return JurassiCraft.NAME;
	}
}
