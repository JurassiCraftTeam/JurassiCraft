package org.jurassicraft.server.block.plant;

import net.minecraft.item.Item;
import org.jurassicraft.server.item.ItemHandler;

public class AjuginuculaSmithiiBlock extends JCBlockCrops8 {
    public AjuginuculaSmithiiBlock() {
        this.seedDropMin = 1;
        this.seedDropMax = 4;
        this.cropDropMin = 2;
        this.cropDropMax = 5;
    }

    @Override
    protected Item getSeed() {
        return ItemHandler.AJUGINUCULA_SMITHII_SEEDS;
    }

    @Override
    protected Item getCrop() {
        return ItemHandler.AJUGINUCULA_SMITHII_LEAVES;
    }
}
