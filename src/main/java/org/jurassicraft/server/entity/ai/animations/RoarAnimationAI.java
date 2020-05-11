package org.jurassicraft.server.entity.ai.animations;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.ai.EntityAIBase;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.Mutex;

public class RoarAnimationAI extends EntityAIBase {
    protected DinosaurEntity dinosaur;

    public RoarAnimationAI(IAnimatedEntity entity) {
        super();
        this.dinosaur = (DinosaurEntity) entity;
        this.setMutexBits(Mutex.ANIMATION);
    }

    @Override
    public boolean shouldExecute() {
    	return !this.dinosaur.isBusy() && this.dinosaur.getAgePercentage() > 75 && this.dinosaur.getRNG().nextDouble() < 0.002;
    }

    @Override
    public void startExecuting() {
    	Animation animation = EntityAnimation.ROARING.get();
        this.dinosaur.setAnimation(animation);
       
        this.dinosaur.playSound(this.dinosaur.getSoundForAnimation(animation), this.dinosaur.getSoundVolume() > 0.0F ? this.dinosaur.getSoundVolume() + 1.25F : 0.0F, this.dinosaur.getSoundPitch());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }
}
