package org.jurassicraft.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.entity.vehicle.VehicleEntity;

@SideOnly(Side.CLIENT)
public class VehicleSound extends EntitySound<VehicleEntity> {
    public VehicleSound(VehicleEntity entity, SoundEvent vehicleMove) {
        super(entity, vehicleMove, SoundCategory.NEUTRAL);
    }

    @Override
    public boolean canRepeat() {
        return true;
    }

    @Override
    public float getVolume() {
    	
    	return ((VehicleEntity) this.entity).getSoundVolume();
    }

    @Override
    public float getPitch() {
        return Math.min(1.0F, this.getVolume()) * 0.5F + 0.7F;
    }
}
