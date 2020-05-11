package org.jurassicraft.client.render.block;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.entity.DisplayBlockEntity;
import org.jurassicraft.server.entity.DinosaurEntity;

public class DisplayBlockRenderer extends TileEntitySpecialRenderer<DisplayBlockEntity> {

    @Override
    public void render(final DisplayBlockEntity tileEntity, final double x, final double y, final double z, final float p_180535_8_, final int p_180535_9_, final float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(x + 0.5, y, z + 0.5);

        GlStateManager.rotate(tileEntity.getRot(), 0.0F, 1.0F, 0.0F);

        final double scale = tileEntity.getEntity().isSkeleton() ? 1.0 : 0.15;
        GlStateManager.scale(scale, scale, scale);

        final DinosaurEntity entity = tileEntity.getEntity();
        if (entity != null) {
        	ClientProxy.MC.getRenderManager().renderEntity(entity, 0, 0, 0, 0, 0, false);
        }

        GlStateManager.popMatrix();
    }
}
