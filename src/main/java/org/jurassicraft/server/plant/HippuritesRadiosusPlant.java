package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

/**
 * Created by Codyr on 11/11/2016.
 */
public class HippuritesRadiosusPlant extends Plant {
    @Override
    public String getName() { return "Hippurites Radiosus"; }

    @Override
    public Block getBlock() {
        return BlockHandler.HIPPURITES_RADIOSUS;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
