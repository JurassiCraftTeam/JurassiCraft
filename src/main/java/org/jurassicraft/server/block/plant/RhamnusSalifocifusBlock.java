package org.jurassicraft.server.block.plant;

import org.jurassicraft.server.item.ItemHandler;

import net.minecraft.item.Item;

public class RhamnusSalifocifusBlock extends JCBlockCrops8 {
	public RhamnusSalifocifusBlock() {
        this.seedDropMin = 1;
        this.seedDropMax = 3;
        this.cropDropMin = 1;
        this.cropDropMax = 2;
	}

	@Override
	protected Item getSeed() {
		return ItemHandler.RHAMNUS_SEEDS;
	}

	@Override
	protected Item getCrop() {
		return ItemHandler.RHAMNUS_BERRIES;
	}

}
