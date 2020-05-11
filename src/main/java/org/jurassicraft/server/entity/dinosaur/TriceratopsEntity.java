package org.jurassicraft.server.entity.dinosaur;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.LegSolver;
import org.jurassicraft.server.entity.LegSolverQuadruped;

public class TriceratopsEntity extends DinosaurEntity {
    public LegSolverQuadruped legSolver;

    public TriceratopsEntity(World world) {
        super(world);
    }

    @Override
    protected LegSolver createLegSolver() {
        return this.legSolver = new LegSolverQuadruped(0.2F, 1.2F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.TRICERATOPS_LIVING;
            case CALLING:
                return SoundHandler.TRICERATOPS_LIVING;
            case DYING:
                return SoundHandler.TRICERATOPS_DEATH;
            case INJURED:
                return SoundHandler.TRICERATOPS_HURT;
		default:
			break;
        }

        return null;
    }
}
