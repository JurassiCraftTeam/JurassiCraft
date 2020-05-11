package org.jurassicraft.server.block.tree;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.jurassicraft.server.tab.TabHandler;

import java.util.Locale;

public class AncientPlanksBlock extends Block {
    public AncientPlanksBlock(TreeType treeType) {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setUnlocalizedName(treeType.name().toLowerCase(Locale.ENGLISH) + "_planks");
        this.setCreativeTab(TabHandler.PLANTS);
    }
}
