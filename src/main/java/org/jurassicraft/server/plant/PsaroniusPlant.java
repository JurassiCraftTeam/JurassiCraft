package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.tree.TreeType;

public class PsaroniusPlant extends Plant {
    @Override
    public String getName() {
        return "Psaronius";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.ANCIENT_SAPLINGS.get(TreeType.PSARONIUS);
    }

    @Override
    public int getHealAmount() {
        return 1000;
    }

    @Override
    public boolean isTree() {
        return true;
    }
}
