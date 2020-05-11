package org.jurassicraft.server.entity.ai.animations;

import net.minecraft.entity.ai.EntityAIBase;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.Mutex;

public class LookAnimationAI extends EntityAIBase {
    protected DinosaurEntity dinosaur;

    public LookAnimationAI(DinosaurEntity dinosaur) {
        super();
        this.dinosaur = dinosaur;
        this.setMutexBits(Mutex.ANIMATION);
    }

    @Override
    public boolean shouldExecute() {
        return !this.dinosaur.isBusy() && this.dinosaur.getRNG().nextDouble() < 0.003;
    }

    @Override
    public void startExecuting() {
        this.dinosaur.setAnimation(this.dinosaur.getRNG().nextBoolean() ? EntityAnimation.LOOKING_LEFT.get() : EntityAnimation.LOOKING_RIGHT.get());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }
}