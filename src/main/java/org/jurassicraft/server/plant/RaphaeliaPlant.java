package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class RaphaeliaPlant extends Plant {
    @Override
    public String getName() {
        return "Raphaelia";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.RAPHAELIA;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
