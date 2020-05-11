package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

/**
 * Created by Codyr on 10/11/2016.
 */
public class CladochonusPlant extends Plant {
    @Override
    public String getName() { return "Cladochonus"; }

    @Override
    public Block getBlock() {
        return BlockHandler.CLADOCHONUS;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}

