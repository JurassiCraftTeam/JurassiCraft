package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

/**
 * Created by Codyr on 10/11/2016.
 */
public class AuloporaPlant extends Plant {
	
    @Override
    public String getName() { return "Aulopora"; }

    @Override
    public Block getBlock() {
        return BlockHandler.AULOPORA;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
