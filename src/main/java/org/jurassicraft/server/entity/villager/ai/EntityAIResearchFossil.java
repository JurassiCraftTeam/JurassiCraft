package org.jurassicraft.server.entity.villager.ai;

import java.util.ArrayList;
import java.util.Random;

import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.FossilBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityAIResearchFossil extends EntityAIMoveToBlock {
	
	/** Villager that is harvesting */
	private final EntityVillager villager;
	/** 0 => discover, -1 => none */
	private int currentTask;

	private static final int explorationRadius = 3;
	private static final int stayAtExploration = 60;

	private int staiedAtExploration = 0;
	private int stayRandom = 0;

	private int standingOffset = 0;

	private Random random;

	public EntityAIResearchFossil(EntityVillager villagerIn, double speedIn) {
		super(villagerIn, speedIn, 16);
		this.villager = villagerIn;
		NBTTagCompound s = this.villager.getEntityData();
		if(!s.hasKey("SpawnLoc")) {
			s.setLong("SpawnLoc", villagerIn.getPos().toLong());
		}
		this.random = new Random();
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {

		double distance = Math.hypot(Math.abs(this.villager.getPositionVector().z - this.getSpawnLocation().getZ()), Math.abs(this.villager.getPositionVector().x - this.getSpawnLocation().getX()));
		if (distance < 10) {

			if (!ForgeEventFactory.getMobGriefingEvent(this.villager.world, this.villager)) {
				return false;
			}
			if (this.runDelay > 0) {
				--this.runDelay;
				return false;
			} else {
				this.runDelay = 20 + this.villager.getRNG().nextInt(10);
				this.currentTask = 0;
				this.staiedAtExploration = 0;
				return this.searchForDestination();
			}
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return this.currentTask >= 0;
	}
	
	public BlockPos getSpawnLocation() {
		return BlockPos.fromLong(this.villager.getEntityData().getLong("SpawnLoc"));
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask() {
		super.updateTask();

		if (this.isAtDestination()) {
			if (this.staiedAtExploration == 0) {
				this.stayRandom = this.random.nextInt(40);
			}
			this.staiedAtExploration++;
			this.setVillagerLook();
			if (this.staiedAtExploration == stayAtExploration / 3) {
				this.villager.world.destroyBlock(this.destinationBlock.add(this.standingOffset * -1, 0, 0), false);
			} else if (this.staiedAtExploration >= stayAtExploration + this.stayRandom) {
				if (!(this.villager.world.getBlockState(this.destinationBlock.add(this.standingOffset * -1, -1, 0))
						.getBlock() instanceof FossilBlock)) {
					this.villager.world.playSound(null, this.villager.getPos(), SoundType.SAND.getPlaceSound(), SoundCategory.BLOCKS, (SoundType.SAND.getVolume() + 1.0F) / 2.0F, SoundType.SAND.getPitch() * 0.8F);
					this.villager.world.setBlockState(this.destinationBlock.add(this.standingOffset * -1, 0, 0),
							Blocks.SAND.getDefaultState());
				}
				this.currentTask = -1;
			}
		}
	}

	/**
	 * Return true to set given position as destination
	 */
	protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
		return true;
	}

	private void setVillagerLook() {
		this.villager.getLookHelper().setLookPosition(
				(double) this.destinationBlock.getX() + this.standingOffset + 0.5D,
				(double) (this.destinationBlock.getY() + 1), (double) this.destinationBlock.getZ() + 0.5D, 10.0F,
				(float) this.villager.getVerticalFaceSpeed());
		this.villager.setRotationYawHead((this.standingOffset == -1) ? 270 : 90);
	}

	private boolean isAtDestination() {
		BlockPos vil = new BlockPos(this.villager);
		return this.getIsAboveDestination()
				|| (vil.getX() == this.destinationBlock.getX() && vil.getZ() == this.destinationBlock.getZ()
						&& Math.abs(vil.getY() - this.destinationBlock.getY()) <= 2);
	}

	private boolean searchForDestination() {
		BlockPos blockpos = new BlockPos(this.villager);

		ArrayList<BlockPos> candidates = new ArrayList<BlockPos>();
		for (int x = -explorationRadius; x <= explorationRadius; x++) {
			for (int z = -explorationRadius; z <= explorationRadius; z++) {
				for (int y = -3; y <= 3; y++) {
					BlockPos tmpPos = new BlockPos(this.villager).add(x, y, z);
					if (villager.world.getBlockState(tmpPos).getBlock() == Blocks.SAND) {
						candidates.add(tmpPos);
					}
				}
			}
		}
		if (candidates.size() > 0) {
			Random random = new Random();
			BlockPos selectedPos = candidates.get(random.nextInt(candidates.size()));
			while (villager.world.getBlockState(selectedPos.up()).getBlock() == Blocks.SAND) {
				selectedPos = selectedPos.up();
			}
			if (selectedPos.getX() > blockpos.getX()) {
				this.standingOffset = -1;
			} else {
				this.standingOffset = 1;
			}
			this.destinationBlock = selectedPos.add(this.standingOffset, 0, 0);
			return true;
		}
		return false;
	}
}
