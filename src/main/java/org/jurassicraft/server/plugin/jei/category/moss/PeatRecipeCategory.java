package org.jurassicraft.server.plugin.jei.category.moss;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.util.LangUtils;
import java.util.ArrayList;
import java.util.List;

public class PeatRecipeCategory implements IRecipeCategory<PeatRecipeWrapper> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(JurassiCraft.MODID, "textures/gui/peat.png");
    private final IDrawable background;
    private final String title;
    private final IDrawableAnimated arrow;

    public PeatRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 132, 45);
        this.title = LangUtils.translate("info.jei.peat_block.name");
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(TEXTURE, 133, 0, 23, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {

    	List<String> tooltips = new ArrayList<String>();
    	
    	if(isSelected(mouseX, mouseY, 7, 7))
    		tooltips.add("Water");
    	else if(isSelected(mouseX, mouseY, 7, 25))
    		tooltips.add(LangUtils.translate("tile.moss_on_peat.name"));
    	else if(isSelected(mouseX, mouseY, 25, 25))
    		tooltips.add(LangUtils.translate("tile.peat.name"));
    	if(isSelected(mouseX, mouseY, 89, 7))
    		tooltips.add("Water");
    	else if(isSelected(mouseX, mouseY, 89, 25))
    		tooltips.add(LangUtils.translate("tile.moss_on_peat.name"));
    	else if(isSelected(mouseX, mouseY, 107, 25))
    		tooltips.add(LangUtils.translate("tile.moss_on_peat.name"));
    	else
    		IRecipeCategory.super.getTooltipStrings(mouseX, mouseY);
    	
    	return tooltips;
    }
    
    public boolean isSelected(double mouseX, double mouseY, double x, double y) {
    	return mouseX >= x && mouseY >= y && mouseX < x + 16 && mouseY < y + 16;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {
    	this.arrow.draw(minecraft, 54, 17);
    	drawBlock(minecraft, 25, 25, BlockHandler.PEAT);
    	drawBlock(minecraft, 7, 25, BlockHandler.PEAT_MOSS);
    	drawWater(minecraft, 7, 7);
    	
    	drawBlock(minecraft, 107, 25, BlockHandler.PEAT_MOSS);
    	drawBlock(minecraft, 89, 25, BlockHandler.PEAT_MOSS);
    	drawWater(minecraft, 89, 7);
    	String text = LangUtils.translate("info.jei.peat.name");
    	int y = 52;
    	for(String s : text.split("<nl>")) {
    		minecraft.fontRenderer.drawString(s, 7, y, 0x0);
    		y += 10;
    	}
    	
    }
    
    public void drawBlock(Minecraft minecraft, int x, int y, Block block) {
		minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		IBlockState state = block.getDefaultState(); 
		BlockModelShapes bms = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
		TextureAtlasSprite sprite;
		List upQuads = null;
		
		IBakedModel model = bms.getModelForState(state);
			upQuads = model.getQuads(state, EnumFacing.UP, 0);
		if (upQuads == null || upQuads.isEmpty()) {
			sprite = bms.getTexture(state);
		} else {
			sprite = ((BakedQuad) upQuads.get(0)).getSprite();
		}
		
		drawSprite(minecraft, x, y, sprite);
	}
    
    public void drawWater(Minecraft minecraft, int x, int y) {
		FluidStack stack = new FluidStack(FluidRegistry.WATER, 1);
		Fluid fluid = stack.getFluid();
		minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		TextureMap blockTextures = minecraft.getTextureMapBlocks();
		TextureAtlasSprite sprite = blockTextures.getTextureExtry(fluid.getStill().toString());
		int color = fluid.getColor(stack);
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;

		GlStateManager.color(red, green, blue, 1.0F);
		drawSprite(minecraft, x, y, sprite);
	}
    
	public void drawSprite(Minecraft minecraft, int x, int y, TextureAtlasSprite sprite) {
		
		double uMin = (double) sprite.getMinU();
		double uMax = (double) sprite.getMaxU();
		double vMin = (double) sprite.getMinV();
		double vMax = (double) sprite.getMaxV();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(x, y + 16, 300).tex(uMin, vMax).endVertex();
		bufferBuilder.pos(x + 16, y + 16, 300).tex(uMax, vMax).endVertex();
		bufferBuilder.pos(x + 16, y, 300).tex(uMax, vMin).endVertex();
		bufferBuilder.pos(x, y, 300).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}

    @Override
    public String getUid() {
        return "jurassicraft.peat";
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
	public String getModName() {
		return JurassiCraft.NAME;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PeatRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
	}
}
