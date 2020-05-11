package org.jurassicraft.server.entity.dinosaur;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.animations.PeckGroundAnimationAI;
import org.jurassicraft.server.entity.ai.animations.RearingUpAnimationAI;

public class MussaurusEntity extends DinosaurEntity {
    public MussaurusEntity(World world){
        super(world);
        this.animationTasks.addTask(3, new PeckGroundAnimationAI(this));
        this.animationTasks.addTask(3, new RearingUpAnimationAI(this));
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.MUSSAURUS_LIVING;
            case DYING:
                return SoundHandler.MUSSAURUS_DEATH;
            case INJURED:
                return SoundHandler.MUSSAURUS_HURT;
            case ATTACKING:
                return SoundHandler.MUSSAURUS_ATTACK;
            case CALLING:
                return SoundHandler.MUSSAURUS_LIVING;
		default:
			break;
        }

        return null;
    }
}
