package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class SmallChainFernPlant extends Plant {
    @Override
    public String getName() {
        return "Small Chain Fern";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.SMALL_CHAIN_FERN;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
