package org.jurassicraft.server.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.jurassicraft.server.tab.TabHandler;

public class WestIndianLilacBerriesItem extends ItemFood {

    public WestIndianLilacBerriesItem() {
        super(1, 0.1F, false);
        this.setHasSubtypes(true);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(MobEffects.POISON, 1400, 1));
    }
}
