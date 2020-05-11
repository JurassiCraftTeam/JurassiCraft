package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import net.minecraft.init.PotionTypes;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.tree.TreeType;
import org.jurassicraft.server.food.FoodHelper;

public class CalamitesPlant extends Plant {
    @Override
    public String getName() {
        return "Calamites";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.ANCIENT_SAPLINGS.get(TreeType.CALAMITES);
    }

    @Override
    public int getHealAmount() {
        return 1000;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(PotionTypes.POISON.getEffects().get(0), 5) };
    }

    @Override
    public boolean isTree() {
        return true;
    }
}
