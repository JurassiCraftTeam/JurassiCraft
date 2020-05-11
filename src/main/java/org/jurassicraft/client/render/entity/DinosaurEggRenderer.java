package org.jurassicraft.client.render.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jurassicraft.client.render.RenderingHandler;
import org.jurassicraft.client.render.entity.dinosaur.DinosaurRenderInfo;
import org.jurassicraft.server.entity.item.DinosaurEggEntity;

public class DinosaurEggRenderer implements IRenderFactory<DinosaurEggEntity> {
	
    @Override
    public Render<? super DinosaurEggEntity> createRenderFor(RenderManager manager) {
        return new Renderer(manager);
    }

    public static class Renderer extends Render<DinosaurEggEntity> {
        public Renderer(RenderManager manager) {
            super(manager);
        }

        @Override
        public void doRender(DinosaurEggEntity egg, double x, double y, double z, float yaw, float partialTicks) {
            GlStateManager.pushMatrix();

            GlStateManager.translate((float) x, (float) y + 1.5F, (float) z);
            GlStateManager.rotate(180.0F - yaw, 0.0F, 1.0F, 0.0F);

            final float scale = 0.75F;
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.scale(1.0F / scale, 1.0F / scale, 1.0F / scale);
            this.bindEntityTexture(egg);
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);

            this.getRenderDef(egg).getEggModel().render(egg, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

            GlStateManager.popMatrix();
        }

        @Override
        protected ResourceLocation getEntityTexture(DinosaurEggEntity entity) {
            return this.getRenderDef(entity).getEggTexture();
        }

        private DinosaurRenderInfo getRenderDef(DinosaurEggEntity entity) {
            return RenderingHandler.INSTANCE.getRenderInfo(entity.getDinosaur());
        }
    }
}