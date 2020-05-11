package org.jurassicraft.client.render.entity.dinosaur;

import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.client.render.entity.DinosaurRenderer;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.GrowthStage;
import org.jurassicraft.server.tabula.TabulaModelHelper;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class DinosaurRenderInfo implements IRenderFactory<DinosaurEntity> {
	
    private static TabulaModel DEFAULT_EGG_MODEL;
    private static ResourceLocation DEFAULT_EGG_TEXTURE;
    private final Map<GrowthStage, AnimatableModel> animatableModels = new EnumMap<>(GrowthStage.class);
    private final Dinosaur dinosaur;
    private final EntityAnimator<?> animator;
    private TabulaModel eggModel;
    private ResourceLocation eggTexture;
    private float shadowSize = 0.65F;
	private AnimatableModel skeletonModels[] = new AnimatableModel[16];
    
    static {
        try {
        	DEFAULT_EGG_MODEL = new TabulaModel(TabulaModelHelper.loadTabulaModel(new ResourceLocation(JurassiCraft.MODID, "models/entities/egg/tyrannosaurus")));
            DEFAULT_EGG_TEXTURE = new ResourceLocation(JurassiCraft.MODID, "textures/entities/egg/tyrannosaurus.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DinosaurRenderInfo(final Dinosaur dinosaur, final EntityAnimator<?> animator, final float shadowSize) {
        this.dinosaur = dinosaur;
        this.animator = animator;
        this.shadowSize = shadowSize;
        
		for (final GrowthStage stage : GrowthStage.values()) {
			if (stage != GrowthStage.SKELETON) {
				this.animatableModels.put(stage, new AnimatableModel(this.dinosaur.getModelContainer(stage), this.getDinosaur().getMetadata(), this.getModelAnimator(stage)));
			} else {
				
				for (int i = 0; i < dinosaur.getMetadata().skeletonPoses().length; i++) {
					this.skeletonModels[i] = new AnimatableModel(this.dinosaur.getSkeletonModel().get(dinosaur.getMetadata().skeletonPoses()[i]), this.getDinosaur().getMetadata(), null);
				}

			}
		}

        try {
        	
        	final ResourceLocation identifier = dinosaur.getIdentifier();
        	final String domain = identifier.getResourceDomain();
        	final String path = identifier.getResourcePath();
            this.eggModel = new TabulaModel(TabulaModelHelper.loadTabulaModel(new ResourceLocation(domain, "models/entities/egg/" + path)));
            this.eggTexture = new ResourceLocation(domain, "textures/entities/egg/" + path + ".png");
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            this.eggModel = DEFAULT_EGG_MODEL;
            this.eggTexture = DEFAULT_EGG_TEXTURE;
        }
    }

    public ModelBase getModel(final GrowthStage stage, final byte variant) {
    	
    	if (!this.dinosaur.doesSupportGrowthStage(stage)) 
    		return this.getModelAdult();
    	if(stage == GrowthStage.SKELETON)
    		return this.skeletonModels[variant];
    	
    	return this.animatableModels.get(stage);
    }

    public ModelBase getEggModel() {
        return this.eggModel;
    }

    public ResourceLocation getEggTexture() {
        return this.eggTexture;
    }

    public EntityAnimator<?> getModelAnimator(final GrowthStage stage) {
        return this.animator;
    }

    public float getShadowSize() {
        return this.shadowSize;
    }

    public Dinosaur getDinosaur() {
        return this.dinosaur;
    }

    @Override
    public Render<? super DinosaurEntity> createRenderFor(final RenderManager manager) {
        return new DinosaurRenderer(this, manager);
    }

    public AnimatableModel getModelAdult() {
        return this.animatableModels.get(GrowthStage.ADULT);
    }
}
