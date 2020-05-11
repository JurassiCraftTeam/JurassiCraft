package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import net.minecraft.init.PotionTypes;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.food.FoodHelper;

public class ScalyTreeFernPlant extends Plant {
    @Override
    public String getName() {
        return "Scaly Tree Fern";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.SCALY_TREE_FERN;
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(PotionTypes.POISON.getEffects().get(0), 100) };
    }
}
