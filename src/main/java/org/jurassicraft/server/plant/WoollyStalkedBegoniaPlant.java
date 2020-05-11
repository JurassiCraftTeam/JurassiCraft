package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class WoollyStalkedBegoniaPlant extends Plant {
    @Override
    public String getName() { return "Woolly Stalked Begonia"; }

    @Override
    public Block getBlock() {
        return BlockHandler.WOOLLY_STALKED_BEGONIA;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
