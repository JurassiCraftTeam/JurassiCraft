package org.jurassicraft.client.model.animation.entity;

import java.util.HashMap;

import org.apache.commons.lang3.BooleanUtils;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.model.animation.EntityAnimator;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.dinosaur.TyrannosaurusEntity;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TyrannosaurusAnimator extends EntityAnimator<TyrannosaurusEntity> {
	
	private HashMap<TyrannosaurusEntity, AnimState> states = new HashMap<>();
	
    @Override
    protected void performAnimations(AnimatableModel model, TyrannosaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
    	
    	if(!entity.isDead && !entity.isCarcass()) {
    	if(!states.containsKey(entity))
    		states.put(entity, new AnimState(0, FootStatus.EQUAL, 0, 0, FootStatus.EQUAL, 0));
    	}else if(states.containsKey(entity)) {
    		states.remove(entity);
    	}
    	
    	final AdvancedModelRenderer waist = model.getCube("bodyHips");
    	final AdvancedModelRenderer stomach = model.getCube("bodyBack");
    	final AdvancedModelRenderer chest = model.getCube("bodyShoulders");
        
    	final AdvancedModelRenderer neck1 = model.getCube("neck1");
    	final AdvancedModelRenderer neck2 = model.getCube("neck2");
    	final AdvancedModelRenderer neck3 = model.getCube("neck3");
    	final AdvancedModelRenderer neck4 = model.getCube("neck4");
    	final AdvancedModelRenderer neck5 = model.getCube("neck5");
    	final AdvancedModelRenderer neck6 = model.getCube("neck6");
    	final AdvancedModelRenderer neck7 = model.getCube("neck7");
    	final AdvancedModelRenderer throat1 = model.getCube("throat1");
    	final AdvancedModelRenderer throat2 = model.getCube("throat2");
    	final AdvancedModelRenderer throat3 = model.getCube("throat3");
        
    	final AdvancedModelRenderer head = model.getCube("head");

    	final AdvancedModelRenderer tail1 = model.getCube("tail1");
    	final AdvancedModelRenderer tail2 = model.getCube("tail2");
    	final AdvancedModelRenderer tail3 = model.getCube("tail3");
    	final AdvancedModelRenderer tail4 = model.getCube("tail4");
    	final AdvancedModelRenderer tail5 = model.getCube("tail5");
    	final AdvancedModelRenderer tail6 = model.getCube("tail6");
    	final AdvancedModelRenderer tail7 = model.getCube("tail7");
    	final AdvancedModelRenderer tail8 = model.getCube("tail8");
    	final AdvancedModelRenderer tail9 = model.getCube("tail9");
        
    	final AdvancedModelRenderer handLeft = model.getCube("handLeft");
    	final AdvancedModelRenderer lowerArmLeft = model.getCube("bicepLeft");

    	final AdvancedModelRenderer handRight = model.getCube("handRight");
    	final AdvancedModelRenderer lowerArmRight = model.getCube("bicepRight");
        
    	final AdvancedModelRenderer leftThigh = model.getCube("thighLeft");
    	final AdvancedModelRenderer rightThigh = model.getCube("thighRight");
        
        final AdvancedModelRenderer[] feet = new AdvancedModelRenderer[] {model.getCube("footLeft"), model.getCube("footRight")};
        
        final AdvancedModelRenderer[] tailParts = new AdvancedModelRenderer[] { tail9, tail8, tail7, tail6, tail5, tail4, tail3, tail2, tail1 };
        final AdvancedModelRenderer[] bodyParts = new AdvancedModelRenderer[] { head, throat3, throat2, throat1, neck7, neck6, neck5, neck4, neck3, neck2, neck1, chest, stomach, waist };
        final AdvancedModelRenderer[] leftArmParts = new AdvancedModelRenderer[] { handLeft, lowerArmLeft };
        final AdvancedModelRenderer[] rightArmParts = new AdvancedModelRenderer[] { handRight, lowerArmRight };

        final float delta = ClientProxy.MC.getRenderPartialTicks();
        
		if (!entity.isDead && !entity.isCarcass()) {

			AnimState statt = this.states.get(entity);
			FootStatus[] tempStatus = new FootStatus[2];
			for (int i = 0; i < feet.length; i++) {

				boolean isLeft = !BooleanUtils.toBoolean(i);

				if (feet[i].rotateAngleX > statt.getAngle(isLeft)) {
					tempStatus[i] = FootStatus.UP;
				} else if (feet[i].rotateAngleX < statt.getAngle(isLeft)) {

					tempStatus[i] = FootStatus.DOWN;

				} else {
					tempStatus[i] = statt.getStatus(isLeft);
				}

				if (tempStatus[i] != statt.getStatus(isLeft)) {

					if (tempStatus[i] == FootStatus.DOWN && feet[i].rotateAngleX > 0.5 && (statt.getTicks(isLeft) + 10) <= ticks) {
						statt.setTicks(isLeft, ticks);
						if(entity.onGround && !entity.isInWater() && (entity.getAnimation() == EntityAnimation.IDLE.get() || entity.getAnimation() == EntityAnimation.WALKING.get() || entity.getAnimation() == EntityAnimation.RUNNING.get() || entity.getAnimation() == EntityAnimation.ROARING.get() || entity.getAnimation() == EntityAnimation.DRINKING.get() || entity.getAnimation() == EntityAnimation.RESTING.get() || entity.getAnimation() == EntityAnimation.INJURED.get())) 
							ClientProxy.MC.player.world.playSound(entity.posX, entity.posY, entity.posZ, SoundHandler.TYRANNOSAURUS_STOMP, SoundCategory.HOSTILE, (float) entity.interpolate(0.1F, 1.0F), entity.getSoundPitch(), false);
					}
					statt.setStatus(isLeft, tempStatus[i]);
				}
				statt.setAngle(isLeft, feet[i].rotateAngleX);
			}

		}
        
        AdvancedModelRenderer leftCalf = model.getCube("calfLeft");
        AdvancedModelRenderer rightCalf = model.getCube("calfRight");
        LegArticulator.articulateBiped(entity, entity.legSolver, waist, leftThigh, leftCalf, rightThigh, rightCalf, 0.4F, 0.4F, delta);

        final float globalSpeed = 0.5F;
        final float globalDegree = 0.5F;

        model.bob(waist, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);

        model.chainWave(tailParts, globalSpeed * 0.5F, globalDegree * 0.05F, 1, f, f1);
        model.chainWave(bodyParts, globalSpeed * 0.5F, globalDegree * 0.025F, 3, f, f1);

        model.chainWave(bodyParts, 0.1F, -0.03F, 3, ticks, 0.25F);
        model.chainWave(rightArmParts, -0.1F, 0.2F, 4, ticks, 0.25F);
        model.chainWave(leftArmParts, -0.1F, 0.2F, 4, ticks, 0.25F);
        model.chainWave(tailParts, 0.1F, -0.1F, 2, ticks, 0.1F);

        model.faceTarget(rotationYaw, rotationPitch, 1.5F, chest, neck1, neck7, head);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
    
    private static class AnimState {
    	
    	public float angleR;
    	public FootStatus statusR;
    	public float ticksR;
    	public float angleL;
    	public FootStatus statusL;
    	public float ticksL;
    	
    	public AnimState(float angleR, FootStatus statusR, float ticksR, float angleL, FootStatus statusL, float ticksL) {
    		
    		this.angleR = angleR;
    		this.statusR = statusR;
    		this.ticksR = ticksR;
    		this.angleL = angleL;
    		this.statusL = statusL;
    		this.ticksL = ticksL;
    		
    	}
    	
    	public float getAngle(boolean isLeft) {
    		return isLeft ? angleL : angleR;
    	}
    	
    	public FootStatus getStatus(boolean isLeft) {
    		return isLeft ? statusL : statusR;
    	}
    	
    	public float getTicks(boolean isLeft) {
    		return isLeft ? ticksL : ticksR;
    	}
    	
    	public void setTicks(boolean isLeft, float ticks) {
    		if(isLeft) {
    			this.ticksL = ticks;
    			return;
    		}
    		this.ticksR = ticks;
    		
    	}
    	
    	public void setStatus(boolean isLeft, FootStatus status) {
    		if(isLeft) {
    			this.statusL = status;
    			return;
    		}
    		this.statusR = status;
    		
    	}
    	
    	public void setAngle(boolean isLeft, float angle) {
    		if(isLeft) {
    			this.angleL = angle;
    			return;
    		}
    		this.angleR = angle;
    		
    	}

    }

    private static enum FootStatus {
    	
    	EQUAL, DOWN, UP;

    }
}
