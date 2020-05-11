package org.jurassicraft.server.entity.dinosaur;

import net.minecraft.entity.ai.EntityAIBase;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.LegSolverBiped;
import org.jurassicraft.server.entity.ai.RaptorLeapEntityAI;
import org.jurassicraft.server.entity.ai.animations.PeckGroundAnimationAI;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class GallimimusEntity extends DinosaurEntity {
    public LegSolverBiped legSolver;

    public GallimimusEntity(World world) {
        super(world);
        this.animationTasks.addTask(3, new PeckGroundAnimationAI(this));
        this.tasks.addTask(1, new RaptorLeapEntityAI(this));
    }

    @Override
    public EntityAIBase getAttackAI() {
        return new RaptorLeapEntityAI(this);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        if (this.getAnimation() != EntityAnimation.LEAP_LAND.get()) {
            super.fall(distance, damageMultiplier);
        }
    }

    @Override
    protected LegSolverBiped createLegSolver() {
        return this.legSolver = new LegSolverBiped(-0.05F, 0.25F, 0.5F);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.GALLIMIMUS_LIVING;
            case CALLING:
                return SoundHandler.GALLIMIMUS_LIVING;
            case DYING:
                return SoundHandler.GALLIMIMUS_DEATH;
            case INJURED:
                return SoundHandler.GALLIMIMUS_HURT;
		default:
			break;
        }

        return null;
    }
}
