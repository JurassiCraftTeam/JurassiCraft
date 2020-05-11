package org.jurassicraft.server.entity.dinosaur;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.jurassicraft.server.entity.SwimmingDinosaurEntity;

public class CoelacanthEntity extends SwimmingDinosaurEntity {
    public CoelacanthEntity(World world) {
        super(world);
        this.target(EntitySquid.class);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
    }


    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        return null;
    }
    
    @Override
    public boolean isNotColliding()
    {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }
    
    @Override
    public boolean isPushedByWater()
    {
        return false;
    }
    
    @Override
    public boolean getCanSpawnHere() {
    	return true;
    }
    
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
    	return type == EnumCreatureType.WATER_CREATURE;
    }
}