package org.jurassicraft.server.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.jurassicraft.server.tab.TabHandler;

public class BasicBlock extends Block {
    public BasicBlock(Material material) {
        super(material);
        this.setCreativeTab(TabHandler.BLOCKS);
    }

    public BasicBlock(Material material, SoundType soundType) {
        super(material);
        this.setSoundType(soundType);
        this.setCreativeTab(TabHandler.BLOCKS);
    }
}
