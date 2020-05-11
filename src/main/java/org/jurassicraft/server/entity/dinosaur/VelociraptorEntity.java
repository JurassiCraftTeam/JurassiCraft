package org.jurassicraft.server.entity.dinosaur;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.GoatEntity;
import org.jurassicraft.server.entity.ai.LeapingMeleeEntityAI;
import org.jurassicraft.server.entity.ai.RaptorLeapEntityAI;

public class VelociraptorEntity extends DinosaurEntity {
    public VelociraptorEntity(World world) {
        super(world);
        this.target(GoatEntity.class, EntityPlayer.class, EntityAnimal.class, EntityVillager.class, DilophosaurusEntity.class, GallimimusEntity.class, ParasaurolophusEntity.class, TriceratopsEntity.class, MicroraptorEntity.class, MussaurusEntity.class);
        this.tasks.addTask(1, new LeapingMeleeEntityAI(this, this.dinosaur.getMetadata().getAttackSpeed()));
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
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
    }


    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.VELOCIRAPTOR_LIVING;
            case DYING:
                return SoundHandler.VELOCIRAPTOR_DEATH;
            case INJURED:
                return SoundHandler.VELOCIRAPTOR_HURT;
            case CALLING:
                return SoundHandler.VELOCIRAPTOR_CALL;
            case ATTACKING:
                return SoundHandler.VELOCIRAPTOR_ATTACK;
		default:
			break;
        }

        return null;
    }

    @Override
    public SoundEvent getBreathingSound() {
        return SoundHandler.VELOCIRAPTOR_BREATHING;
    }
}
