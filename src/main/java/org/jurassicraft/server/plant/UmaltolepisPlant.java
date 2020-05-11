package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class UmaltolepisPlant extends Plant {
    @Override
    public String getName() {
        return "Umaltolepis";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.UMALTOLEPIS;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
