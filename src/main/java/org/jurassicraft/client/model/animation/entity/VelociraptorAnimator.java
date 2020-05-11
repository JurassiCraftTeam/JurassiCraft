package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.server.entity.dinosaur.VelociraptorEntity;

@SideOnly(Side.CLIENT)
public class VelociraptorAnimator extends EntityAnimator<VelociraptorEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, VelociraptorEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer waist = model.getCube("body3");
    	final AdvancedModelRenderer chest = model.getCube("body2");
    	final AdvancedModelRenderer shoulders = model.getCube("body1");
    	final AdvancedModelRenderer neck1 = model.getCube("neck1");
    	final AdvancedModelRenderer neck2 = model.getCube("neck2");
    	final AdvancedModelRenderer neck3 = model.getCube("neck3");
    	final AdvancedModelRenderer neck4 = model.getCube("neck4");
    	final AdvancedModelRenderer head = model.getCube("Head");
    	final AdvancedModelRenderer tail1 = model.getCube("tail1");
    	final AdvancedModelRenderer tail2 = model.getCube("tail2");
    	final AdvancedModelRenderer tail3 = model.getCube("tail3");
    	final AdvancedModelRenderer tail4 = model.getCube("tail4");
    	final AdvancedModelRenderer tail5 = model.getCube("tail5");
    	final AdvancedModelRenderer tail6 = model.getCube("tail6");

    	final AdvancedModelRenderer upperArmRight = model.getCube("Right arm");
    	final AdvancedModelRenderer upperArmLeft = model.getCube("Left arm");
    	final AdvancedModelRenderer lowerArmRight = model.getCube("Right forearm");
    	final AdvancedModelRenderer lowerArmLeft = model.getCube("Left forearm");
    	final AdvancedModelRenderer Hand_Right = model.getCube("Right hand");
    	final AdvancedModelRenderer Hand_Left = model.getCube("Left hand");

    	final AdvancedModelRenderer leftThigh = model.getCube("Left thigh");
    	final AdvancedModelRenderer rightThigh = model.getCube("Right thigh");

        final AdvancedModelRenderer[] rightArmParts = new AdvancedModelRenderer[] { Hand_Right, lowerArmRight, upperArmRight };
        final AdvancedModelRenderer[] leftArmParts = new AdvancedModelRenderer[] { Hand_Left, lowerArmLeft, upperArmLeft };
        final AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail6, tail5, tail4, tail3, tail2, tail1 };
        final AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[] { waist, chest, shoulders, neck1, neck2, neck3, neck4, head };

        final float globalSpeed = 1.0F;
        final float globalDegree = 1.0F;

        model.bob(waist, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, globalSpeed * 0.5F, globalDegree * 0.05F, 1, limbSwing, limbSwingAmount);
        model.chainSwing(tailParts, globalSpeed * 0.5F, globalDegree * 0.1F, 2, limbSwing, limbSwingAmount);
        model.chainWave(bodyParts, globalSpeed * 0.5F, globalDegree * 0.025F, 3, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, 0.1F, 0.05F, 2, ticks, 0.25F);
        model.chainWave(bodyParts, 0.1F, -0.03F, 5, ticks, 0.25F);
        model.chainWave(rightArmParts, 0.1F, -0.1F, 4, ticks, 0.25F);
        model.chainWave(leftArmParts, 0.1F, -0.1F, 4, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 2.0F, neck1, head);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
