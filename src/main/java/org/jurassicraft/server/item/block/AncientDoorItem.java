package org.jurassicraft.server.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import org.jurassicraft.server.tab.TabHandler;

public class AncientDoorItem extends ItemDoor {
    public AncientDoorItem(Block block) {
        super(block);
        this.setCreativeTab(TabHandler.PLANTS);
    }
}
