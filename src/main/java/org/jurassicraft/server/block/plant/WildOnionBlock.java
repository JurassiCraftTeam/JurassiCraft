package org.jurassicraft.server.block.plant;

import net.minecraft.item.Item;
import org.jurassicraft.server.item.ItemHandler;

public class WildOnionBlock extends JCBlockCrops7 {
    public WildOnionBlock() {
        this.seedDropMin = 0;
        this.seedDropMax = 0;
        this.cropDropMin = 1;
        this.cropDropMax = 4;
    }

    @Override
    protected Item getSeed() {
        return ItemHandler.WILD_ONION;
    }

    @Override
    protected Item getCrop() {
        return ItemHandler.WILD_ONION;
    }
}
