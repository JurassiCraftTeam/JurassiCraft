package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class SmallRoyalFernPlant extends Plant {
    @Override
    public String getName() {
        return "Small Royal Fern";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.SMALL_ROYAL_FERN;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
