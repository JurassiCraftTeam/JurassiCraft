package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

/**
 * Created by Codyr on 30/10/2016.
 */
public class EnallheliaPlant extends Plant {
    @Override
    public String getName() { return "Enallhelia"; }

    @Override
    public Block getBlock() {
        return BlockHandler.ENALLHELIA;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
