package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;

/**
 * Created by Codyr on 11/11/2016.
 */
public class StylophyllopsisPlant extends Plant {
    @Override
    public String getName() { return "Stylophyllopsis"; }

    @Override
    public Block getBlock() {
        return BlockHandler.STYLOPHYLLOPSIS;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
