package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.server.entity.dinosaur.MicroraptorEntity;

@SideOnly(Side.CLIENT)
public class MicroraptorAnimator extends EntityAnimator<MicroraptorEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, MicroraptorEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	if (entity.getAnimation() == EntityAnimation.GLIDING.get()) {
            GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
        }

    	final AdvancedModelRenderer upperArmRight = model.getCube("RightArm1");
    	final AdvancedModelRenderer lowerArmRight = model.getCube("RightArm2");
    	final AdvancedModelRenderer rightHand = model.getCube("RightArm3");
    	final AdvancedModelRenderer upperArmLeft = model.getCube("LeftArm1");
    	final AdvancedModelRenderer lowerArmLeft = model.getCube("LeftArm2");
    	final AdvancedModelRenderer leftHand = model.getCube("LeftArm3");

    	final AdvancedModelRenderer rightThigh = model.getCube("RightLeg1");
    	final AdvancedModelRenderer leftThigh = model.getCube("LeftLeg1");

    	final AdvancedModelRenderer tail1 = model.getCube("Tail1");
    	final AdvancedModelRenderer tail2 = model.getCube("Tail1");
    	final AdvancedModelRenderer tail3 = model.getCube("Tail1");
    	final AdvancedModelRenderer tail4 = model.getCube("Tail1");

    	final AdvancedModelRenderer shoulders = model.getCube("Body1");
    	final AdvancedModelRenderer waist = model.getCube("Body2");

    	final AdvancedModelRenderer neck1 = model.getCube("Neck1");
    	final AdvancedModelRenderer neck2 = model.getCube("Neck2");
    	final AdvancedModelRenderer head = model.getCube("Head");

        final AdvancedModelRenderer[] rightArmParts = new AdvancedModelRenderer[] { rightHand, lowerArmRight, upperArmRight };
        final AdvancedModelRenderer[] leftArmParts = new AdvancedModelRenderer[] { leftHand, lowerArmLeft, upperArmLeft };
        final AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail4, tail3, tail2, tail1 };
        final AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[] { waist, shoulders, neck1, neck2, head };

        final float globalSpeed = 1.0F;
        final float globalDegree = 3.0F;

        model.bob(shoulders, globalSpeed * 1.0F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(rightThigh, globalSpeed * 1.0F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(leftThigh, globalSpeed * 1.0F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, 0.1F, 0.05F, 2, ticks, 0.5F);
        model.chainWave(bodyParts, 0.1F, -0.03F, 5, ticks, 0.5F);
        model.chainWave(rightArmParts, 0.1F, -0.1F, 4, ticks, 0.5F);
        model.chainWave(leftArmParts, 0.1F, -0.1F, 4, ticks, 0.5F);

        model.faceTarget(rotationYaw, rotationPitch, 2.0F, neck1, head);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
