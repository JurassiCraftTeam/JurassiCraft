package org.jurassicraft.server.plugin.jei.category.cultivate;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.entity.CultivatorBlockEntity;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.dinosaur.DinosaurMetadata;
import org.jurassicraft.server.plugin.jei.JurassiCraftJEIPlugin;

import java.util.List;
import java.util.function.IntSupplier;

public class CultivatorRecipeCategory implements IRecipeCategory<CultivatorRecipeWrapper> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(JurassiCraft.MODID, "textures/gui/cultivator_jei.png");

    private final IDrawable background;
    private final String title;

    private List<NutrientBar> nutrientBarList;

    public CultivatorRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 169, 90);
        this.title = I18n.translateToLocal("tile.cultivator.name");
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        nutrientBarList.forEach(NutrientBar::render);
    }

    @Override
    public String getUid() {
        return JurassiCraftJEIPlugin.CULTIVATEOR;
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
    public void setRecipe(IRecipeLayout recipeLayout, CultivatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();
        stackGroup.init(0, true, 40, 6);
        stackGroup.set(0, ingredients.getInputs(ItemStack.class).get(0));
        stackGroup.init(1, false, 105, 6);
        stackGroup.set(1, ingredients.getOutputs(ItemStack.class).get(0));

        DinosaurMetadata metadata = recipeWrapper.getDinosaur().getMetadata();

        nutrientBarList = Lists.newArrayList(
        		new NutrientBar(metadata::getProximates, 0),
                new NutrientBar(metadata::getMinerals, 1),
                new NutrientBar(metadata::getVitamins, 2),
                new NutrientBar(metadata::getLipids, 3)
        );
    }

    @Override
    public String getModName() {
        return JurassiCraft.NAME;
    }

    private class NutrientBar {

        private final int id;

        private final int value;

        private NutrientBar(IntSupplier supplier, int id) {
            this.id = id;
            this.value = supplier.getAsInt();
        }

        private void render() {
            this.drawTexturedModalRect(9, 30 + (id * 16), 0, 91 + (id * 9), value * 150 / CultivatorBlockEntity.MAX_NUTRIENTS, 9);
        }


        public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos((double)(x + 0), (double)(y + height), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
            bufferbuilder.pos((double)(x + width), (double)(y + height), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
            bufferbuilder.pos((double)(x + width), (double)(y + 0), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
            bufferbuilder.pos((double)(x + 0), (double)(y + 0), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
            tessellator.draw();
        }

    }
}
