package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

public class WildPotatoPlant extends Plant {
    @Override
    public String getName() {
        return "Wild Potato";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.WILD_POTATO_PLANT;
    }

    @Override
    public int getHealAmount() {
        return 3000;
    }
}
