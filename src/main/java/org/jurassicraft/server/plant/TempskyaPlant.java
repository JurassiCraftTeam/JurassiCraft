package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class TempskyaPlant extends Plant {
    @Override
    public String getName() {
        return "Tempskya";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.TEMPSKYA;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
