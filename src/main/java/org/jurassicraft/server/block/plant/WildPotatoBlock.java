package org.jurassicraft.server.block.plant;

import net.minecraft.item.Item;
import org.jurassicraft.server.item.ItemHandler;

public class WildPotatoBlock extends JCBlockCrops6 {
    public WildPotatoBlock() {
        this.seedDropMin = 1;
        this.seedDropMax = 3;
        this.cropDropMin = 1;
        this.cropDropMax = 2;
    }

    @Override
    protected Item getSeed() {
        return ItemHandler.WILD_POTATO_SEEDS;
    }

    @Override
    protected Item getCrop() {
        return ItemHandler.WILD_POTATO;
    }
}
