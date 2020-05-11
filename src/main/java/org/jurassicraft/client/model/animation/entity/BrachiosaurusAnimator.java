package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.entity.dinosaur.BrachiosaurusEntity;

@SideOnly(Side.CLIENT)
public class BrachiosaurusAnimator extends EntityAnimator<BrachiosaurusEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, BrachiosaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer head = model.getCube("head");
    	final AdvancedModelRenderer neck1 = model.getCube("Neck 1");
    	final AdvancedModelRenderer neck2 = model.getCube("neck2");
    	final AdvancedModelRenderer neck3 = model.getCube("neck3");
    	final AdvancedModelRenderer neck4 = model.getCube("neck4");
    	final AdvancedModelRenderer neck5 = model.getCube("neck5");
    	final AdvancedModelRenderer neck6 = model.getCube("neck6");
    	final AdvancedModelRenderer neck7 = model.getCube("neck7");

    	final AdvancedModelRenderer tail1 = model.getCube("tail1");
    	final AdvancedModelRenderer tail2 = model.getCube("tail2");
    	final AdvancedModelRenderer tail3 = model.getCube("tail3");
    	final AdvancedModelRenderer tail4 = model.getCube("tail4");
    	final AdvancedModelRenderer tail5 = model.getCube("tail5");

    	final AdvancedModelRenderer[] neckParts = new AdvancedModelRenderer[] { head, neck7, neck6, neck5, neck4, neck3, neck2, neck1 };
    	final AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail5, tail4, tail3 };
    	final AdvancedModelRenderer[] tailParts2 = new AdvancedModelRenderer[] { tail5, tail4, tail3, tail2, tail1 };

        float delta = ClientProxy.MC.getRenderPartialTicks();
        final AdvancedModelRenderer root = model.getCube("hips");
        final AdvancedModelRenderer backLeftThigh = model.getCube("top leg left");
        final AdvancedModelRenderer backLeftCalf = model.getCube("bottom leg left");
        final AdvancedModelRenderer backRightThigh = model.getCube("top leg right");
        final AdvancedModelRenderer backRightCalf = model.getCube("bottom leg right");
        final AdvancedModelRenderer frontLeftThigh = model.getCube("front left top leg");
        final AdvancedModelRenderer frontLeftCalf = model.getCube("bottom front left leg");
        final AdvancedModelRenderer frontRightThigh = model.getCube("front right top leg");
        final AdvancedModelRenderer frontRightCalf = model.getCube("bottom front right leg");
        LegArticulator.articulateQuadruped(entity, entity.legSolver, root, neck1,
            backLeftThigh, backLeftCalf, backRightThigh, backRightCalf, frontLeftThigh, frontLeftCalf, frontRightThigh, frontRightCalf, 
            0.25F, 0.4F, -0.2F, -0.3F,
            delta
        );

        final float globalSpeed = 0.4F;
        final float globalHeight = 0.5F;

        model.chainWave(tailParts, globalSpeed * 0.25F, globalHeight * 1.0F, 3, ticks, 0.025F);
        model.chainWave(neckParts, globalSpeed * 0.25F, globalHeight * 0.25F, -3, ticks, 0.025F);

        entity.tailBuffer.applyChainSwingBuffer(tailParts2);
    }
}
