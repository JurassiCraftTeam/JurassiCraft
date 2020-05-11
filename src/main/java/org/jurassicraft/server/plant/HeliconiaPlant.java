package org.jurassicraft.server.plant;

import org.jurassicraft.server.block.BlockHandler;

import net.minecraft.block.Block;

/**
 * Created by Codyr on 22/01/2017.
 */
public class HeliconiaPlant extends Plant {
    @Override
    public String getName() {
        return "Heliconia";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.HELICONIA;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }

    @Override
    public boolean isPrehistoric() {
        return false;
    }
}
