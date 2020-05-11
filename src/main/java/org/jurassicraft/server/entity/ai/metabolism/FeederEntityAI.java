package org.jurassicraft.server.entity.ai.metabolism;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jurassicraft.server.block.entity.FeederBlockEntity;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.Mutex;
import org.jurassicraft.server.util.GameRuleHandler;

public class FeederEntityAI extends EntityAIBase {
    protected DinosaurEntity dinosaur;
    private static ThreadPoolExecutor tpe = new ThreadPoolExecutor(0, 3, 10, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    protected volatile Path path;
    protected volatile BlockPos feederPosition;
    protected volatile boolean searched = false;

    public FeederEntityAI(DinosaurEntity dinosaur) {
        this.dinosaur = dinosaur;
        this.setMutexBits(Mutex.METABOLISM);
    }

	@Override
	public boolean shouldExecute() {
		if (!this.dinosaur.isCarcass() && !this.dinosaur.isMovementBlocked() && GameRuleHandler.DINO_METABOLISM.getBoolean(this.dinosaur.world)) {
			if (this.dinosaur.getMetabolism().isHungry()) {
				World world = this.dinosaur.world;
				if (this.searched == false && tpe.getActiveCount() < 2) {

					this.searched = true;
					try {
						tpe.execute(new ThreadRunnable(this, this.dinosaur) {

							@Override
							public void run() {
								synchronized (world) {
									synchronized (this.ai) {
										try {
											BlockPos feeder = this.entity.getClosestFeeder();
											if (feeder != null) {
												this.ai.feederPosition = feeder;
												this.ai.path = this.entity.getNavigator().getPathToPos(this.ai.feederPosition);
											}
										} catch (Exception e) {

										}
									}
									this.ai.searched = false;
								}
							}
						});
					} catch (RejectedExecutionException e) {

					}
				}
			}
		}

		return true;
	}

    @Override
    public void updateTask() {
    	
        if (this.path == null)
        	return;
		try {
			this.dinosaur.getNavigator().setPath(this.path, 1.0);

			if (!this.dinosaur.world.isRemote && (this.dinosaur.getDistance(this.feederPosition.getX(), this.feederPosition.getY(), this.feederPosition.getZ()) <= this.dinosaur.width * this.dinosaur.width || this.path.isFinished())) {
				TileEntity tile = this.dinosaur.world.getTileEntity(this.feederPosition);
				if (tile instanceof FeederBlockEntity) {
					FeederBlockEntity feeder = (FeederBlockEntity) tile;
					if (feeder.canFeedDinosaur(this.dinosaur)) {
						feeder.setOpen(true);
						feeder.setFeeding(this.dinosaur);
					}
				}
				this.resetTask();
			}
		} catch (NullPointerException e) {

		}
    }

    @Override
    public void resetTask() {
        this.path = null;
        this.dinosaur.getNavigator().clearPath();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.dinosaur != null && this.path != null;
    }
    
    abstract class ThreadRunnable implements Runnable {

    	final DinosaurEntity entity;
    	final FeederEntityAI ai;

    	ThreadRunnable(FeederEntityAI feederEntityAI, DinosaurEntity entity) {
    		this.ai = feederEntityAI;
    		this.entity = entity;
    	}
    }
}
