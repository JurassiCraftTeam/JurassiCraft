package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class DicksoniaPlant extends Plant {
    @Override
    public String getName() {
        return "Dicksonia";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.DICKSONIA;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
