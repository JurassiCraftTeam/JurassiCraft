package org.jurassicraft.server.entity.dinosaur;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import scala.reflect.internal.Trees.This;
import java.util.HashMap;
import java.util.Map.Entry;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.entity.GoatEntity;
import org.jurassicraft.server.entity.LegSolverBiped;

public class TyrannosaurusEntity extends DinosaurEntity {

    public LegSolverBiped legSolver;
    private int stepCount = 0;

    public TyrannosaurusEntity(World world) {
        super(world);
        this.target(GoatEntity.class, EntityPlayer.class, EntityAnimal.class, EntityVillager.class, EntityMob.class, DilophosaurusEntity.class, GallimimusEntity.class, TriceratopsEntity.class, ParasaurolophusEntity.class, VelociraptorEntity.class, MussaurusEntity.class);
    }
    @Override
    protected LegSolverBiped createLegSolver() {
        return this.legSolver = new LegSolverBiped(-0.5F, 1.0F, 1.0F);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {

        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.TYRANNOSAURUS_LIVING;
            case CALLING:
                return SoundHandler.TYRANNOSAURUS_CALL;
            case ROARING:
                return (this.variants.containsKey(animation) ? SoundHandler.TYRANNOSAURUS_ROAR[this.variants.get(animation)] : SoundHandler.TYRANNOSAURUS_ROAR[0]);
            case DYING:
                return SoundHandler.TYRANNOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.TYRANNOSAURUS_HURT;
            case DRINKING:
            	return SoundHandler.TYRANNOSAURUS_DRINKING;
            case ATTACKING:
                return SoundHandler.TYRANNOSAURUS_ATTACK;
            
            default:
                return null;
        }
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
    }
    
    @Override
    public SoundEvent getBreathingSound() {
        return SoundHandler.TYRANNOSAURUS_BREATHING;
    }
    
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (world.isRemote) {
		
			if (!this.isRendered && this.onGround && !this.isInWater() && world.isRemote) {
				if (this.moveForward > 0 && (this.posX - this.prevPosX > 0 || this.posZ - this.prevPosZ > 0)
						&& this.stepCount <= 0) {
					this.playSound(SoundHandler.TYRANNOSAURUS_STOMP, (float) this.interpolate(0.1F, 1.0F),
							this.getSoundPitch());
					this.stepCount = 65;
				}
				this.stepCount -= this.moveForward * 9.5;
			}
		}
	}
   
}
