package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class RhamnusSalifocifusPlant extends Plant {
    @Override
    public String getName() {
        return "Rhamnus Salifocifus";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.RHAMNUS_SALICIFOLIUS_PLANT;
    }

    @Override
    public int getHealAmount() {
        return 3000;
    }
}
