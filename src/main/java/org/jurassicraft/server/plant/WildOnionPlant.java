package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class WildOnionPlant extends Plant {
    @Override
    public String getName() {
        return "Wild Onion";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.WILD_ONION;
    }

    @Override
    public int getHealAmount() {
        return 3000;
    }
}
