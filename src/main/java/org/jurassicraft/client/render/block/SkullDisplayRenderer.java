package org.jurassicraft.client.render.block;

import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import java.util.Random;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.SkullDisplay;
import org.jurassicraft.server.block.entity.SkullDisplayEntity;
import org.jurassicraft.server.tabula.TabulaModelHelper;
import org.lwjgl.opengl.GL11;

public class SkullDisplayRenderer extends TileEntitySpecialRenderer<SkullDisplayEntity> {
	
    @Override
    public void render(SkullDisplayEntity tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	final IBlockState blockState = tile.getWorld().getBlockState(tile.getPos());
        if (blockState.getBlock() == BlockHandler.SKULL_DISPLAY) {
            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);

            final EnumFacing.Axis axis = blockState.getValue(SkullDisplay.FACING).getAxis();
            final boolean horizontal;
			if (axis == EnumFacing.Axis.Y) {
				horizontal = true;
			} else {
				horizontal = false;
			}
         
            GlStateManager.rotate(tile.getAngle(), 0.0F, 1.0F, 0.0F);

            if (tile.model == null) {
            	if(tile.hasData()) {
				try {
					final String dinosaur = tile.getDinosaur().getIdentifier().getResourcePath().toString();
					final boolean isFossilized = tile.isFossilized();		
					if(horizontal) {
						tile.model = new TabulaModel(TabulaModelHelper.loadTabulaModel(new ResourceLocation(JurassiCraft.MODID, "models/block/skull_display/tyrannosaurus_horizontal")));
						tile.texture = new ResourceLocation(JurassiCraft.MODID, "textures/blocks/skull_display/" + dinosaur + "_" + (isFossilized ? "fossilized" : "fresh") + "_" + "vertical.png");
					}else {
						tile.model = new TabulaModel(TabulaModelHelper.loadTabulaModel(new ResourceLocation(JurassiCraft.MODID, "models/block/skull_display/tyrannosaurus_vertical")));
						tile.texture = new ResourceLocation(JurassiCraft.MODID, "textures/blocks/skull_display/" + dinosaur + "_" + (isFossilized ? "fossilized" : "fresh") + "_" + "horizontal.png");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            	}
			}
			GlStateManager.scale(2, -2, 2);
			if(!horizontal) 
			   GlStateManager.translate(0, 0.2, -0.25);
			
			GlStateManager.translate(0.0F, -1.26F, 0.0F);
			if(tile.model != null) {
				ClientProxy.MC.getTextureManager().bindTexture(tile.texture);
				if(!tile.hasStand() && horizontal) {
					tile.model.getCube("Standard part1").isHidden = true;
					tile.model.getCube("Standard part2").isHidden = true;
					tile.model.getCube("head").defaultRotationX = -0.07f;
					tile.model.getCube("head").defaultPositionY = 14.1f;
					tile.model.getCube("jawLower1").defaultRotationX = 0.155f;
				}
				tile.model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			}
			GlStateManager.disableBlend();
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
        }
    }
}
