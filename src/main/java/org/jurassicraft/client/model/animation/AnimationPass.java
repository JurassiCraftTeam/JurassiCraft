package org.jurassicraft.client.model.animation;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.api.Animatable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AnimationPass {
    protected final Map<Animation, float[][][]> animations;
    protected final Map<Animation, byte[]> poseCounts;
    protected final PosedCuboid[][] poses;
    protected float[][] rotationIncrements;
    protected float[][] positionIncrements;
    protected float[][] prevRotationIncrements;
    protected float[][] prevPositionIncrements;
    protected int poseCount;
    protected int poseIndex;
    protected float poseLength;

    protected float animationTick;
    protected float prevTicks;

    protected AdvancedModelRenderer[] parts;
    protected PosedCuboid[] pose;

    protected Animation animation;

    protected boolean useInertia;

    protected float inertiaFactor;

    protected float limbSwing;
    protected float limbSwingAmount;

    public AnimationPass(Map<Animation, float[][][]> animations, PosedCuboid[][] poses, Map<Animation, byte[]> poseCounts, boolean useInertia) {
    	this.animations = animations;
    	this.poseCounts = poseCounts;
        this.poses = poses;
        this.useInertia = useInertia;
    }

    public void init(AdvancedModelRenderer[] parts, Animatable entity) {
        this.parts = parts;
        
        this.prevRotationIncrements = new float[parts.length][3];
        this.prevPositionIncrements = new float[parts.length][3];
        this.rotationIncrements = new float[parts.length][3];
        this.positionIncrements = new float[parts.length][3];
        this.animation = this.getRequestedAnimation(entity);
        this.initPoseModel((byte) 0);
        this.initAnimation(entity, this.getRequestedAnimation(entity));
        this.initAnimationTicks(entity);

        this.prevTicks = 0.0F;
        this.initIncrements(entity);
        this.performAnimations(entity, 0.0F, 0.0F, 0.0F);
    }
    
    public byte getValidVariant(Animatable entity, Animation animation) {
    	
    	return (byte) (EntityAnimation.getAnimation(animation).hasVariants(entity.getEntityClass()) ? entity.getAnimationVariant(animation) : 0);
    }

    public void initPoseModel(byte variant) {
    	
        float[][] pose = this.animations.get(this.animation)[variant];
        if (pose != null) {
            
            this.poseIndex = 0;
            this.poseCount = getPoseCount(variant);
            this.pose = this.poses[(int) pose[this.poseIndex][0]];
        }
    }

    private int getPoseCount(byte variant) {
    	
    	return this.poseCounts.get(this.animation)[variant];

	}

	protected void initIncrements(Animatable entity) {
        float animationDegree = this.getAnimationDegree(entity);
        for (int partIndex = 0; partIndex < Math.min(this.pose.length, this.parts.length); partIndex++) {
            AdvancedModelRenderer part = this.parts[partIndex];
            PosedCuboid nextPose = this.pose[partIndex];

            float[] rotationIncrements = this.rotationIncrements[partIndex];
            float[] positionIncrements = this.positionIncrements[partIndex];
            
            rotationIncrements[0] = (nextPose.rotationX - (part.defaultRotationX + this.prevRotationIncrements[partIndex][0])) * animationDegree;
            rotationIncrements[1] = (nextPose.rotationY - (part.defaultRotationY + this.prevRotationIncrements[partIndex][1])) * animationDegree;
            rotationIncrements[2] = (nextPose.rotationZ - (part.defaultRotationZ + this.prevRotationIncrements[partIndex][2])) * animationDegree;

            positionIncrements[0] = (nextPose.positionX - (part.defaultPositionX + this.prevPositionIncrements[partIndex][0])) * animationDegree;
            positionIncrements[1] = (nextPose.positionY - (part.defaultPositionY + this.prevPositionIncrements[partIndex][1])) * animationDegree;
            positionIncrements[2] = (nextPose.positionZ - (part.defaultPositionZ + this.prevPositionIncrements[partIndex][2])) * animationDegree;
       
        }
    }

    public void initAnimation(Animatable entity, Animation animation) {
        this.animation = animation;
        if (this.animations.get(animation)[this.getValidVariant(entity, animation)] == null) {
            this.animation = EntityAnimation.IDLE.get();
        }
    }

    protected float calculateInertiaFactor() {
        float inertiaFactor = this.animationTick / this.poseLength;

        if (this.useInertia && EntityAnimation.getAnimation(this.animation).useInertia()) {
            inertiaFactor = (float) (Math.sin(Math.PI * (inertiaFactor - 0.5D)) * 0.5D + 0.5D);
        }

        return Math.min(1.0F, Math.max(0.0F, inertiaFactor));
    }

    public void performAnimations(Animatable entity, float limbSwing, float limbSwingAmount, float ticks) {

        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        Animation requestedAnimation = this.getRequestedAnimation(entity);
        if ((requestedAnimation != this.animation)) {
            this.setAnimation(entity, requestedAnimation);
        }

        if (this.poseIndex >= this.poseCount) {
            this.poseIndex = this.poseCount - 1;
            
        }

        this.inertiaFactor = this.calculateInertiaFactor();

        if (this.pose == null) {
            JurassiCraft.getLogger().error("Trying to animate to a null pose array");
        } else {
            for (int partIndex = 0; partIndex < Math.min(this.parts.length, this.pose.length); partIndex++) {
                if (this.pose[partIndex] == null) {
                    JurassiCraft.getLogger().error("Part " + partIndex + " in pose is null");
                } else {
                    this.applyRotations(partIndex);
                    this.applyTranslations(partIndex);
                }
            }
        }

        if (this.updateAnimationTick(entity, ticks)) {
            this.onPoseFinish(entity, ticks);
        }

        this.prevTicks = ticks;
    }

    public boolean updateAnimationTick(Animatable entity, float ticks) {
    
    	float[][] pose = this.animations.get(this.animation)[this.getValidVariant(entity, this.animation)];
        float incrementAmount = (ticks - this.prevTicks) * this.getAnimationSpeed(entity);
        if (this.animationTick < 0.0F) {
            this.animationTick = 0.0F;
        }
        if (!EntityAnimation.getAnimation(this.animation).shouldHold() || this.poseIndex < this.poseCount) {
        
            this.animationTick += incrementAmount;

            if (this.animationTick >= this.poseLength) {
                this.animationTick = this.poseLength;
            	
                return true;
            }

            return false;
        } else {
            if (this.animationTick < this.poseLength) {
                this.animationTick += incrementAmount;

                if (this.animationTick >= this.poseLength) {
                    this.animationTick = this.poseLength;
                }
            } else {
                this.animationTick = this.poseLength;
            }

            return false;
        }
        
    }

    protected void applyRotations(int partIndex) {
        AdvancedModelRenderer part = this.parts[partIndex];

        float[] rotationIncrements = this.rotationIncrements[partIndex];

        part.rotateAngleX += (rotationIncrements[0] * this.inertiaFactor + this.prevRotationIncrements[partIndex][0]);
        part.rotateAngleY += (rotationIncrements[1] * this.inertiaFactor + this.prevRotationIncrements[partIndex][1]);
        part.rotateAngleZ += (rotationIncrements[2] * this.inertiaFactor + this.prevRotationIncrements[partIndex][2]);
    }

    protected void applyTranslations(int partIndex) {
        AdvancedModelRenderer part = this.parts[partIndex];

        float[] translationIncrements = this.positionIncrements[partIndex];

        part.rotationPointX += (translationIncrements[0] * this.inertiaFactor + this.prevPositionIncrements[partIndex][0]);
        part.rotationPointY += (translationIncrements[1] * this.inertiaFactor + this.prevPositionIncrements[partIndex][1]);
        part.rotationPointZ += (translationIncrements[2] * this.inertiaFactor + this.prevPositionIncrements[partIndex][2]);
    }

    protected void setPose(int poseIndex, byte variant) {
    	float[][] pose = this.animations.get(this.animation)[variant];
        this.poseCount = getPoseCount(/*pose*/variant);
        
        this.poseIndex = poseIndex;
        this.pose = this.poses[(int) pose[this.poseIndex][0]];
    }

    protected void initAnimationTicks(Animatable entity) {
        this.startAnimation(entity);
        if (EntityAnimation.getAnimation(this.animation).shouldHold()) {
        	float[][] pose = this.animations.get(this.animation)[this.getValidVariant(entity, this.animation)];
            this.poseIndex = this.poseCount - 1;
            this.animationTick = pose[this.poseIndex][1];
        } else {
            this.animationTick = 0;
        }
    }

    protected void startAnimation(Animatable entity) {
        float[][] pose = this.animations.get(this.animation)[this.getValidVariant(entity, this.animation)];
        if (pose != null) {
            this.pose = this.poses[(int) pose[this.poseIndex][0]];
            this.poseLength = Math.max(1, pose[this.poseIndex][1]);
            this.animationTick = 0;

            this.initIncrements(entity);
        }
    }

    protected void setPose(Animatable entity, float ticks) {
    	float[][] pose = this.animations.get(this.animation)[this.getValidVariant(entity, this.animation)];
    	this.poseCount = getPoseCount(/*pose*/this.getValidVariant(entity, this.animation));
    	this.pose = this.poses[(int) pose[this.poseIndex][0]];
        this.poseLength = pose[this.poseIndex][1];
        this.animationTick = 0;
        this.prevTicks = ticks;
        this.initIncrements(entity);
        
    }

    protected void onPoseFinish(Animatable entity, float ticks) {

        if (this.incrementPose()) {
        	 
        	
            this.setAnimation(entity, this.isEntityAnimationDependent() ? EntityAnimation.IDLE.get() : this.getRequestedAnimation(entity));
        } else {
            this.updatePreviousPose();
        }
        this.setPose(entity, ticks);
        
    }

    public boolean incrementPose() {
        this.poseIndex++;
       
        if (this.poseIndex >= this.poseCount) {
     
            EntityAnimation animation = EntityAnimation.getAnimation(this.animation);
            if (animation != null && animation.shouldHold()) {

                this.poseIndex = this.poseCount - 1;
                

                
            } else {
                this.poseIndex = 0;
                return true;
            }
        }
        return false;
    }

    protected void setAnimation(Animatable entity, Animation requestedAnimation) {
        this.updatePreviousPose();
       
        if (this.animations.get(requestedAnimation) != null && this.animations.get(requestedAnimation)[this.getValidVariant(entity, requestedAnimation)] != null && !(this.animation != EntityAnimation.IDLE.get() && this.animation == requestedAnimation && !this.isLooping())) {
            this.animation = requestedAnimation;
        } else {
            this.animation = EntityAnimation.IDLE.get();
        }

        this.setPose(0, this.getValidVariant(entity, requestedAnimation));

        this.startAnimation(entity);
    }

    protected void updatePreviousPose() {
        for (int partIndex = 0; partIndex < this.parts.length; partIndex++) {
            this.prevRotationIncrements[partIndex][0] += this.rotationIncrements[partIndex][0] * this.inertiaFactor;
            this.prevRotationIncrements[partIndex][1] += this.rotationIncrements[partIndex][1] * this.inertiaFactor;
            this.prevRotationIncrements[partIndex][2] += this.rotationIncrements[partIndex][2] * this.inertiaFactor;

            this.prevPositionIncrements[partIndex][0] += this.positionIncrements[partIndex][0] * this.inertiaFactor;
            this.prevPositionIncrements[partIndex][1] += this.positionIncrements[partIndex][1] * this.inertiaFactor;
            this.prevPositionIncrements[partIndex][2] += this.positionIncrements[partIndex][2] * this.inertiaFactor;
        }
    }

    protected float getAnimationSpeed(Animatable entity) {
        return 1.0F;
    }

    protected float getAnimationDegree(Animatable entity) {
        return 1.0F;
    }

    protected Animation getRequestedAnimation(Animatable entity) {
        return entity.getAnimation();
    }

    protected boolean isEntityAnimationDependent() {
        return true;
    }

    public boolean isLooping() {
        return false;
    }
}
