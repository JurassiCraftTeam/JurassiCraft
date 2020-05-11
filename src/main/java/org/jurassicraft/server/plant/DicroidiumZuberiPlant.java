package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class DicroidiumZuberiPlant extends Plant {
    @Override
    public String getName() {
        return "Dicroidium Zuberi";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.DICROIDIUM_ZUBERI;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
