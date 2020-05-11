package org.jurassicraft.server.entity.ai;

import org.jurassicraft.server.entity.DinosaurEntity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DinosaurWanderAvoidWater extends DinosaurWanderEntityAI {

    public DinosaurWanderAvoidWater(DinosaurEntity creatureIn, double speedIn) {
	super(creatureIn, speedIn, 1);
    }
    
    @Override
    protected boolean innerShouldStopExcecuting() { 
        return this.entity.canDinoSwim() || !this.entity.isInWater();
    }
    
    @Override
    protected boolean outterShouldExecute() {
        return this.entity.shouldEscapeWaterFast();
    }
    
    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }
    
    @Override
    protected Vec3d getWanderPosition() {
	Vec3d vec3d = null;
	for(int i = 0; i < 100; i++) {
	    Vec3d vec = RandomPositionGenerator.getLandPos(this.entity, 32, 7);
	    if(vec == null) {
		continue;
	    } else if(vec3d == null || this.entity.getPositionVector().distanceTo(vec) < this.entity.getPositionVector().distanceTo(vec3d)) {
		vec3d = vec;
	    }
	}
        return vec3d == null ? super.getWanderPosition() : vec3d;
    }

}