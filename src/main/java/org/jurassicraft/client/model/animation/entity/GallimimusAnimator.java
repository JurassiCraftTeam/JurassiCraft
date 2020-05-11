package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.entity.dinosaur.GallimimusEntity;

@SideOnly(Side.CLIENT)
public class GallimimusAnimator extends EntityAnimator<GallimimusEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, GallimimusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer neck1 = model.getCube("Neck part 1");
    	final AdvancedModelRenderer neck2 = model.getCube("Neck part 2");
    	final AdvancedModelRenderer neck3 = model.getCube("Neck part 3");
    	final AdvancedModelRenderer neck4 = model.getCube("Neck part 4");
    	final AdvancedModelRenderer neck5 = model.getCube("Neck part 5");

    	final AdvancedModelRenderer tail1 = model.getCube("Tail part 1");
    	final AdvancedModelRenderer tail2 = model.getCube("Tail part 2");
    	final AdvancedModelRenderer tail3 = model.getCube("Tail part 3");
    	final AdvancedModelRenderer tail4 = model.getCube("Tail part 4");
    	final AdvancedModelRenderer tail5 = model.getCube("Tail part 5");
    	final AdvancedModelRenderer tail6 = model.getCube("Tail part 6");

    	final AdvancedModelRenderer body1 = model.getCube("Body 1");
    	final AdvancedModelRenderer body2 = model.getCube("Body 2");
    	final AdvancedModelRenderer body3 = model.getCube("Body 3");

    	final AdvancedModelRenderer head = model.getCube("Head");

    	final AdvancedModelRenderer upperArmLeft = model.getCube("Left upper arm");
    	final AdvancedModelRenderer upperArmRight = model.getCube("Right upper arm");

    	final AdvancedModelRenderer lowerArmRight = model.getCube("Right lower arm");
    	final AdvancedModelRenderer lowerArmLeft = model.getCube("Left lower arm");

    	final AdvancedModelRenderer handRight = model.getCube("Right wrist");
    	final AdvancedModelRenderer handLeft = model.getCube("Left wrist");

    	final AdvancedModelRenderer leftThigh = model.getCube("Left thigh");
    	final AdvancedModelRenderer rightThigh = model.getCube("Right thigh");

    	final AdvancedModelRenderer leftCalf = model.getCube("Left shin");
    	final AdvancedModelRenderer rightCalf = model.getCube("Right shin");

        final AdvancedModelRenderer[] body = new AdvancedModelRenderer[] { head, neck5, neck4, neck3, neck2, neck1, body1, body2, body3 };

        final AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] { tail6, tail5, tail4, tail3, tail2, tail1 };

        final AdvancedModelRenderer[] armLeft = new AdvancedModelRenderer[] { handLeft, lowerArmLeft, upperArmLeft };
        final AdvancedModelRenderer[] armRight = new AdvancedModelRenderer[] { handRight, lowerArmRight, upperArmRight };

        final float delta = ClientProxy.MC.getRenderPartialTicks();
        LegArticulator.articulateBiped(entity, entity.legSolver, body1, leftThigh, leftCalf, rightThigh, rightCalf, 1.0F, 1.4F, delta);

        final float globalSpeed = 1.5F;
        final float globalDegree = 1.0F;

        model.bob(body1, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);

        model.chainWave(tail, globalSpeed * 0.25F, globalDegree * 0.05F, 1, f, f1);
        model.chainSwing(tail, globalSpeed * 0.25F, globalDegree * 0.2F, 2, f, f1);
        model.chainWave(body, globalSpeed * 0.25F, globalDegree * 0.025F, 3, f, f1);

        model.chainWave(tail, 0.1F, 0.05F, 1, ticks, 0.25F);
        model.chainWave(body, 0.1F, -0.05F, 4, ticks, 0.25F);
        model.chainWave(armRight, 0.1F, -0.15F, 4, ticks, 0.25F);
        model.chainWave(armLeft, 0.1F, -0.15F, 4, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1, neck2, neck3, neck4, head);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
