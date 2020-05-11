package org.jurassicraft.client.render.entity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.event.ClientEventHandler;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.client.render.entity.dinosaur.DinosaurRenderInfo;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.dinosaur.DinosaurMetadata;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.GrowthStage;
import org.jurassicraft.server.entity.OverlayType;
import java.awt.Color;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class DinosaurRenderer extends RenderLiving<DinosaurEntity> {
	
    public final Dinosaur dinosaur;
    public final DinosaurRenderInfo renderInfo;
    public final Random random;

    public DinosaurRenderer(final DinosaurRenderInfo renderInfo, final RenderManager renderManager) {
    
        super(renderManager, null, 0);
        this.dinosaur = renderInfo.getDinosaur();
        this.random = new Random();
        this.renderInfo = renderInfo;
        this.addLayer(new LayerOverlay(this, this.dinosaur.getMetadata().getOverlays()));
    }

    @Override
    public void preRenderCallback(final DinosaurEntity entity, final float partialTick) {
    	final DinosaurMetadata metadata = this.dinosaur.getMetadata();
    	final float scaleModifier = entity.getAttributes().getScaleModifier();
    	final float scale = (float) entity.interpolate(metadata.getScaleInfant(), metadata.getScaleAdult()) * scaleModifier;
        this.shadowSize = scale * this.renderInfo.getShadowSize();

        GlStateManager.translate(metadata.getOffsetX() * scale, metadata.getOffsetY() * scale, metadata.getOffsetZ() * scale);

        final String name = entity.getCustomNameTag();
        switch (name) {
            case "Kashmoney360":
            case "JTGhawk137":
                GlStateManager.scale(0.1F, scale, scale);
                break;
            case "Gegy":
                GlStateManager.scale(scale, 0.01F, scale);
                break;
            case "Notch":
                GlStateManager.scale(scale * 2, scale * 2, scale * 2);
                break;
            case "jglrxavpok":
                GlStateManager.scale(scale, scale, scale * -1);
                break;
            case "Wyn":
            	final int color = Color.HSBtoRGB((entity.world.getTotalWorldTime() % 1000) / 100f, 1f, 1f);
                GlStateManager.color((color & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f);
            default:
                GlStateManager.scale(scale, scale, scale);
                break;
        }

    }

    @Override
    public void doRender(final DinosaurEntity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        this.mainModel = this.renderInfo.getModel(entity.getGrowthStage(), (byte) entity.getSkeletonVariant());
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(final DinosaurEntity entity) {
        GrowthStage growthStage = entity.getGrowthStage();
        if (!this.dinosaur.doesSupportGrowthStage(growthStage)) {
            growthStage = GrowthStage.ADULT;
        }
        return growthStage == GrowthStage.SKELETON ? (entity.getIsFossile() ? this.dinosaur.getMaleTexture(growthStage) : this.dinosaur.getFemaleTexture(growthStage)) : (entity.isMale() ? this.dinosaur.getMaleTexture(growthStage) : this.dinosaur.getFemaleTexture(growthStage));
   
    }

    @Override
    protected void applyRotations(final DinosaurEntity entity, final float p_77043_2_, final float p_77043_3_, final float partialTicks) {
        GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    }

    @SideOnly(Side.CLIENT)
    public static class LayerOverlay implements LayerRenderer<DinosaurEntity> {
        private final DinosaurRenderer renderer;
        private final OverlayType[] type;

        public LayerOverlay(final DinosaurRenderer renderer, final OverlayType[] type) {
            this.renderer = renderer;
            this.type = type;
        }

        @Override
        public void doRenderLayer(final DinosaurEntity entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float age, final float yaw, final float pitch, final float scale) {
            if (!entity.isInvisible()) {
				for (OverlayType type : this.type) {
					if (entity.areEyelidsClosed() && type == OverlayType.EYELID) {
						renderOverlay(type, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);
					} else if (!entity.areEyelidsClosed() && type == OverlayType.EYE) {
						renderOverlay(type, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);

					} else if (type != OverlayType.EYE && type != OverlayType.EYELID) {
						renderOverlay(type, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);
					}
				}
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
        
        private void renderOverlay(final OverlayType type, final DinosaurEntity entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float age, final float yaw, final float pitch, final float scale) {
        	final ResourceLocation texture = this.renderer.dinosaur.getOverlayTextures(type, entity);
            if (texture != null) {
            	final ITextureObject textureObject = ClientProxy.MC.getTextureManager().getTexture(texture);
                if (textureObject != TextureUtil.MISSING_TEXTURE) {
                    this.renderer.bindTexture(texture);
                    this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);
                }
            }
        }
    }
}
