package org.jurassicraft.client.render.block;

import net.ilexiconn.llibrary.LLibrary;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.model.ResetControlTabulaModel;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.entity.FeederBlockEntity;
import org.jurassicraft.server.block.machine.FeederBlock;
import org.jurassicraft.server.tabula.TabulaModelHelper;
import org.lwjgl.opengl.GL11;

public class FeederRenderer extends TileEntitySpecialRenderer<FeederBlockEntity> {
	
    private ResetControlTabulaModel model;
    private ResourceLocation texture;

    public FeederRenderer() {
        try {
        	this.model = new ResetControlTabulaModel(TabulaModelHelper.loadTabulaModel(new ResourceLocation(JurassiCraft.MODID, "models/block/feeder")));
            this.model.setResetEachFrame(false);
            this.texture = new ResourceLocation(JurassiCraft.MODID, "textures/blocks/feeder.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(final FeederBlockEntity tile, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
    	final IBlockState blockState = tile.getWorld().getBlockState(tile.getPos());

        if (blockState.getBlock() == BlockHandler.FEEDER) {
            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);

            final EnumFacing facing = blockState.getValue(FeederBlock.FACING);

            final float rotation = facing.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? -90.0F : 90.0F;

            final EnumFacing.Axis axis = facing.getAxis();

            if (axis == EnumFacing.Axis.Y) {
                GlStateManager.rotate(rotation - 90.0F, 0.0F, 0.0F, 1.0F);
            } else if (axis == EnumFacing.Axis.X) {
                GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            } else if (axis == EnumFacing.Axis.Z) {
                GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.translate(0.0F, 1.0F, 0.0F);

            final double scale = 1.0;
            GlStateManager.scale(scale, -scale, scale);

            ClientProxy.MC.getTextureManager().bindTexture(this.texture);

            final float openAnimation = Math.max(0.0F, Math.min(20.0F, tile.openAnimation + LLibrary.PROXY.getPartialTicks() * (tile.openAnimation - tile.prevOpenAnimation)));

            this.model.getCube("Lid 1").rotateAngleX = (float) Math.toRadians((openAnimation / 20.0F) * 99.13F);
            this.model.getCube("Lid 2").rotateAngleX = (float) Math.toRadians((openAnimation / 20.0F) * -99.13F);

            this.model.render(null, 0, 0, 0, 0, 0, 0.0625F);

            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }
}
