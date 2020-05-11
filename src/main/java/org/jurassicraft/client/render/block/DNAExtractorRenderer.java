package org.jurassicraft.client.render.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.OrientedBlock;
import org.jurassicraft.server.block.entity.DNAExtractorBlockEntity;
import org.lwjgl.opengl.GL11;

public class DNAExtractorRenderer extends TileEntitySpecialRenderer<DNAExtractorBlockEntity> {
	
    @Override
    public void render(final DNAExtractorBlockEntity tileEntity, final double x, final double y, final double z, final float p_180535_8_, final int p_180535_9_, final float alpha) {
    	final IBlockState state = tileEntity.getWorld().getBlockState(tileEntity.getPos());
        final ItemStack extraction = tileEntity.getStackInSlot(0);

        if (extraction != null && state.getBlock() == BlockHandler.DNA_EXTRACTOR) {
            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);

            EnumFacing facing = state.getValue(OrientedBlock.FACING);

            if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                facing = facing.getOpposite();
            }

            final int rotation = facing.getHorizontalIndex() * 90;

            GlStateManager.rotate(rotation - 180, 0, 1, 0);

            final double scale = 1.0;
            GlStateManager.scale(-scale, -scale, scale);

            ClientProxy.MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            final RenderItem renderItem = ClientProxy.MC.getRenderItem();

            GlStateManager.translate(0.225, 1.25, -0.125);
            GlStateManager.rotate(-90, 1, 0, 0);
            GlStateManager.scale(-0.75 * 0.5, -0.75 * 0.5, 0.75 * 0.5);
            renderItem.renderItem(extraction, renderItem.getItemModelMesher().getItemModel(extraction));

            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }
}
