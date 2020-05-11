package org.jurassicraft.client.model.animation.entity;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.server.entity.GoatEntity;

@SideOnly(Side.CLIENT)
public class GoatAnimator extends EntityAnimator<GoatEntity> {
	
    @Override
    protected void performAnimations(AnimatableModel model, GoatEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        
    	final AdvancedModelRenderer neck1 = model.getCube("Neck base");
    	final AdvancedModelRenderer neck2 = model.getCube("Throat");
    	final AdvancedModelRenderer head = model.getCube("Head lower");
        final AdvancedModelRenderer[] neck = new AdvancedModelRenderer[] { head, neck2, neck1 };

        model.chainWave(neck, 0.125F, 1.0F, 3, ticks, 0.025F);
        model.faceTarget(rotationYaw, rotationPitch, 1.0F, head);
    }
}
