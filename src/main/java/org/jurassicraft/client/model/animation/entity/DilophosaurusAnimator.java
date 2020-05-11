package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.server.entity.dinosaur.DilophosaurusEntity;

@SideOnly(Side.CLIENT)
public class DilophosaurusAnimator extends EntityAnimator<DilophosaurusEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, DilophosaurusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer frillLeftBottom = model.getCube("Frill Lower Left");
    	final AdvancedModelRenderer frillLeftTop = model.getCube("Frill Upper Left");

    	final AdvancedModelRenderer frillRightBottom = model.getCube("Frill Lower Right");
    	final AdvancedModelRenderer frillRightTop = model.getCube("Frill Upper Right");

        final boolean hasTarget = entity.hasTarget() && !entity.isCarcass();

        frillLeftBottom.showModel = hasTarget;
        frillLeftTop.showModel = hasTarget;
        frillRightBottom.showModel = hasTarget;
        frillRightTop.showModel = hasTarget;

        final AdvancedModelRenderer head = model.getCube("Head");

        final AdvancedModelRenderer neck1 = model.getCube("Neck Base");
        final AdvancedModelRenderer neck2 = model.getCube("Neck 2");
        final AdvancedModelRenderer neck3 = model.getCube("Neck 3");
        final AdvancedModelRenderer neck4 = model.getCube("Neck 4");
        final AdvancedModelRenderer neck5 = model.getCube("Neck 5");
        final AdvancedModelRenderer neck6 = model.getCube("Neck 6");

        final AdvancedModelRenderer body1 = model.getCube("Body FRONT");
        final AdvancedModelRenderer body2 = model.getCube("Body MIDDLE");
        final AdvancedModelRenderer body3 = model.getCube("Body REAR");

        final AdvancedModelRenderer tail1 = model.getCube("Tail BASE");
        final AdvancedModelRenderer tail2 = model.getCube("Tail 2");
        final AdvancedModelRenderer tail3 = model.getCube("Tail 3");
        final AdvancedModelRenderer tail4 = model.getCube("Tail 4");
        final AdvancedModelRenderer tail5 = model.getCube("Tail 5");
        final AdvancedModelRenderer tail6 = model.getCube("Tail 6");

        final AdvancedModelRenderer rightThigh = model.getCube("Leg Right UPPER");
        final AdvancedModelRenderer leftThigh = model.getCube("Leg Left UPPER");

        final AdvancedModelRenderer upperArmRight = model.getCube("Right arm");
        final AdvancedModelRenderer upperArmLeft = model.getCube("Left arm");

        final AdvancedModelRenderer lowerArmRight = model.getCube("Right forearm");
        final AdvancedModelRenderer lowerArmLeft = model.getCube("Left forearm");

        final AdvancedModelRenderer handRight = model.getCube("Right hand");
        final AdvancedModelRenderer handLeft = model.getCube("Left hand");

        final AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[] { head, neck6, neck5, neck4, neck3, neck2, neck1, body1, body2, body3 };
        final AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail6, tail5, tail4, tail3, tail2, tail1 };

        final AdvancedModelRenderer[] armRight = new AdvancedModelRenderer[] { handRight, lowerArmRight, upperArmRight };
        final AdvancedModelRenderer[] armLeft = new AdvancedModelRenderer[] { handLeft, lowerArmLeft, upperArmLeft };

        final float globalSpeed = 1.0F;
        final float globalDegree = 1.0F;

        model.bob(body3, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, globalSpeed * 0.5F, globalDegree * 0.05F, 1, limbSwing, limbSwingAmount);
        model.chainSwing(tailParts, globalSpeed * 0.5F, globalDegree * 0.1F, 2, limbSwing, limbSwingAmount);
        model.chainWave(bodyParts, globalSpeed * 0.5F, globalDegree * 0.025F, 3, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, 0.15F, -0.03F, 2, ticks, 0.25F);
        model.chainWave(bodyParts, 0.15F, 0.03F, 3.5F, ticks, 0.25F);
        model.chainWave(armRight, 0.15F, -0.1F, 4, ticks, 0.25F);
        model.chainWave(armLeft, 0.15F, -0.1F, 4, ticks, 0.25F);
        model.chainSwing(tailParts, 0.15F, -0.1F, 3, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1, head);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
