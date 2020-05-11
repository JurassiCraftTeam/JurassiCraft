package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class BristleFernPlant extends Plant {
    @Override
    public String getName() {
        return "Bristle Fern";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.BRISTLE_FERN;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
