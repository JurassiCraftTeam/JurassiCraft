package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class CryPansyPlant extends Plant {
    @Override
    public String getName() {
        return "Cry Pansy";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.CRY_PANSY;
    }

    @Override
    public int getHealAmount() {
        return 250;
    }
}
