package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class LiriodendritesPlant extends Plant {
    @Override
    public String getName() {
        return "Liriodendrites";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.LIRIODENDRITES;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
