package org.jurassicraft.client.model.animation;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.server.api.Animatable;
import org.jurassicraft.server.entity.AnimalMetadata;
import org.jurassicraft.server.tabula.TabulaModelHelper;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author jabelar
 *         This class handles per-entity animations.
 */
@SideOnly(Side.CLIENT)
public class JabelarAnimationHandler<ENTITY extends EntityLivingBase & Animatable> {

    private final AnimationPass DEFAULT_PASS;
    private final AnimationPass MOVEMENT_PASS;
    private final AnimationPass ON_LAND_PASS;

    public JabelarAnimationHandler(ENTITY entity, AnimatableModel model, PosedCuboid[][] poses, Map<Animation, float[][][]> poseSequences, Map<Animation, byte[]> poseCount, boolean useInertialTweens) {
    	this.DEFAULT_PASS = new AnimationPass(poseSequences, poses, poseCount, useInertialTweens);
        this.MOVEMENT_PASS = new MovementAnimationPass(poseSequences, poses, poseCount, useInertialTweens);
        this.ON_LAND_PASS = new OnLandAnimationPass(poseSequences, poses, poseCount, useInertialTweens);
        this.init(entity, model);
    }

    public static AnimatableModel loadModel(final ResourceLocation model, final AnimalMetadata animal) {
        try {
            return new AnimatableModel(TabulaModelHelper.loadTabulaModel(model), animal, null);
        } catch (NullPointerException | IOException e) {
            JurassiCraft.getLogger().error("Could not load Tabula model " + model, e);
        }
        return null;
    }

    private void init(final ENTITY entity, final AnimatableModel model) {
    	final AdvancedModelRenderer[] parts = this.getParts(model);
        this.DEFAULT_PASS.init(parts, entity);
        this.MOVEMENT_PASS.init(parts, entity);
        if (entity.isMarineCreature()) {
            this.ON_LAND_PASS.init(parts, entity);
        }
    }

    public void performAnimations(final ENTITY entity, final float limbSwing, final float limbSwingAmount, final float ticks) {
        this.DEFAULT_PASS.performAnimations(entity, limbSwing, limbSwingAmount, ticks);
        if (!entity.isCarcass()) {
            this.MOVEMENT_PASS.performAnimations(entity, limbSwing, limbSwingAmount, ticks);
            if (entity.isMarineCreature()) {
                this.ON_LAND_PASS.performAnimations(entity, limbSwing, limbSwingAmount, ticks);
            }
        }
    }

    private AdvancedModelRenderer[] getParts(final AnimatableModel model) {
    	final AdvancedModelRenderer[] parts = new AdvancedModelRenderer[model.getIdentifierCubes().size()];
        int i = 0;
        for (AdvancedModelRenderer part : model.getIdentifierCubes().values()) {
            parts[i++] = part;
        }
        return parts;
    }
}
