package org.jurassicraft.server.block.tree;

import net.minecraft.block.state.IBlockState;
import org.jurassicraft.server.tab.TabHandler;

import java.util.Locale;

public class AncientSlabHalfBlock extends AncientSlabBlock {
    public AncientSlabHalfBlock(TreeType type, IBlockState state) {
        super(type, state);
        this.setCreativeTab(TabHandler.PLANTS);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
