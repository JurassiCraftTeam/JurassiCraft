package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

/**
 * Created by Codyr on 11/11/2016.
 */
public class LithostrotionPlant extends Plant {
    @Override
    public String getName() { return "Lithostrotion"; }

    @Override
    public Block getBlock() {
        return BlockHandler.LITHOSTROTION;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}

