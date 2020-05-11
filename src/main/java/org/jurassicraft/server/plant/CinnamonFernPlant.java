package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class CinnamonFernPlant extends Plant {
    @Override
    public String getName() {
        return "Cinnamon Fern";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.CINNAMON_FERN;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
