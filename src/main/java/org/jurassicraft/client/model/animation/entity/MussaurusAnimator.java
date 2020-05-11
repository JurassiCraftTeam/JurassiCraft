package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.server.entity.dinosaur.MussaurusEntity;

@SideOnly(Side.CLIENT)
public class MussaurusAnimator extends EntityAnimator<MussaurusEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, MussaurusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer head = model.getCube("Head1");

    	final AdvancedModelRenderer neck1 = model.getCube("Neck1");
    	final AdvancedModelRenderer neck2 = model.getCube("Neck2");
    	final AdvancedModelRenderer neck3 = model.getCube("Neck3");

    	final AdvancedModelRenderer body1 = model.getCube("Body1");
    	final AdvancedModelRenderer body2 = model.getCube("Body2");

    	final AdvancedModelRenderer tail1 = model.getCube("Tail1");
    	final AdvancedModelRenderer tail2 = model.getCube("Tail2");
    	final AdvancedModelRenderer tail3 = model.getCube("Tail3");
    	final AdvancedModelRenderer tail4 = model.getCube("Tail4");
    	final AdvancedModelRenderer tail5 = model.getCube("Tail5");
    	final AdvancedModelRenderer tail6 = model.getCube("Tail6");
        final AdvancedModelRenderer tail7 = model.getCube("Tail7");
        final AdvancedModelRenderer tail8 = model.getCube("Tail8");

        final AdvancedModelRenderer[] body = new AdvancedModelRenderer[] { head, neck3, neck2, neck1, body2, body1 };
        final AdvancedModelRenderer[] tail = new AdvancedModelRenderer[] { tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail1 };

        model.chainWave(body, 0.075F, -0.01F, -2, ticks, 1.0F);
        model.chainWave(tail, 0.075F, -0.01F, 2, ticks, 1.0F);

        model.faceTarget(rotationYaw, rotationPitch, 2.0F, neck1, neck2, neck3);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
