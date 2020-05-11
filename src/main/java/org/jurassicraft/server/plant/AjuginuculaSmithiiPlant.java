package org.jurassicraft.server.plant;

import net.minecraft.block.Block;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.food.FoodHelper;

public class AjuginuculaSmithiiPlant extends Plant {
    @Override
    public String getName() {
        return "Ajuginucula Smithii";
    }

    @Override
    public Block getBlock() {
        return BlockHandler.AJUGINUCULA_SMITHII;
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(new PotionEffect(MobEffects.SPEED, 100), 100) };
    }
}
