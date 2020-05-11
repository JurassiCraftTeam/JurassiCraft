package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.entity.dinosaur.ParasaurolophusEntity;

@SideOnly(Side.CLIENT)
public class ParasaurolophusAnimator extends EntityAnimator<ParasaurolophusEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, ParasaurolophusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer head = model.getCube("Head");

    	final AdvancedModelRenderer neck1 = model.getCube("Neck1");
    	final AdvancedModelRenderer neck2 = model.getCube("Neck2");
    	final AdvancedModelRenderer neck3 = model.getCube("Neck3");

    	final AdvancedModelRenderer waist = model.getCube("Body1");
    	final AdvancedModelRenderer body2 = model.getCube("Body2");
    	final AdvancedModelRenderer body3 = model.getCube("Body3");

    	final AdvancedModelRenderer tail1 = model.getCube("Tail1");
        final AdvancedModelRenderer tail2 = model.getCube("Tail2");
        final AdvancedModelRenderer tail3 = model.getCube("Tail3");
        final AdvancedModelRenderer tail4 = model.getCube("Tail4");
        final AdvancedModelRenderer tail5 = model.getCube("Tail5");
        final AdvancedModelRenderer tail6 = model.getCube("Tail6");

        final AdvancedModelRenderer upperArmRight = model.getCube("Upper Arm Right");
        final AdvancedModelRenderer lowerArmRight = model.getCube("Lower Arm Right");
        final AdvancedModelRenderer rightHand = model.getCube("Right Hand");

        final AdvancedModelRenderer upperArmLeft = model.getCube("Upper Arm Left");
        final AdvancedModelRenderer lowerArmLeft = model.getCube("Lower Arm Left");
        final AdvancedModelRenderer leftHand = model.getCube("Left Hand");

        final AdvancedModelRenderer leftThigh = model.getCube("Thigh Left");
        final AdvancedModelRenderer rightThigh = model.getCube("Thigh Right");

        final AdvancedModelRenderer leftCalf = model.getCube("Left Calf 1");
        final AdvancedModelRenderer rightCalf = model.getCube("Right Calf 1");

        final AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] { tail6, tail5, tail4, tail3, tail2, tail1 };
        final AdvancedModelRenderer[] body = new AdvancedModelRenderer[] { head, neck3, neck2, neck1, waist, body2, body3 };

        final float globalSpeed = 0.5F;
        final float globalDegree = 0.8F;

        model.bob(waist, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);

        model.chainWave(tail, globalSpeed * 0.25F, globalDegree * 0.1F, 1, f, f1);
        model.chainSwing(tail, globalSpeed * 0.25F, globalDegree * 0.25F, 2, f, f1);
        model.chainWave(body, globalSpeed * 0.25F, globalDegree * 0.05F, 3, f, f1);

        model.walk(neck1, 0.1F, 0.07F, false, -1F, 0F, ticks, 0.25F);
        model.walk(head, 0.1F, 0.07F, true, 0F, 0F, ticks, 0.25F);
        model.walk(waist, 0.1F, 0.04F, false, 0F, 0F, ticks, 0.25F);
        model.walk(upperArmRight, 0.1F, 0.1F, false, -1F, 0F, ticks, 0.25F);
        model.walk(upperArmLeft, 0.1F, 0.1F, false, -1F, 0F, ticks, 0.25F);
        model.walk(lowerArmRight, 0.1F, 0.1F, true, -1.5F, 0F, ticks, 0.25F);
        model.walk(lowerArmLeft, 0.1F, 0.1F, true, -1.5F, 0F, ticks, 0.25F);
        model.walk(rightHand, 0.1F, 0.1F, false, -2F, 0F, ticks, 0.25F);
        model.walk(leftHand, 0.1F, 0.1F, false, -2F, 0F, ticks, 0.25F);

        final float delta = ClientProxy.MC.getRenderPartialTicks();
        LegArticulator.articulateQuadruped(entity, entity.legSolver, waist, neck1,
                leftThigh, leftCalf, rightThigh, rightCalf, upperArmLeft, lowerArmLeft, upperArmRight, lowerArmRight,
                0.5F, 0.5F, -1.4F, -1.4F,
                delta
        );

        model.chainWave(tail, 0.1F, -0.02F, 2, ticks, 1F);

        model.faceTarget(rotationYaw, rotationPitch, 2.0F, neck1, neck2);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
