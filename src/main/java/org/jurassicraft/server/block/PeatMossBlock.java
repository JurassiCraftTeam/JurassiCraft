package org.jurassicraft.server.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.jurassicraft.server.api.IncubatorEnvironmentItem;
import org.jurassicraft.server.tab.TabHandler;

public class PeatMossBlock extends Block implements IncubatorEnvironmentItem {
    public PeatMossBlock() {
        super(Material.GROUND);
        this.setSoundType(SoundType.GROUND);
        this.setHardness(0.5F);
        this.setCreativeTab(TabHandler.PLANTS);
    }
}
