package org.jurassicraft.server.plugin.jei.category.moss;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.JurassicIngredientItem;

public interface PeatItem extends JurassicIngredientItem {
	
    static PeatItem getPeatItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();

            if (item instanceof ItemBlock) {
                Block block = ((ItemBlock) item).getBlock();

                if (block instanceof PeatItem) {
                    return (PeatItem) block;
                }
            } else if (item instanceof PeatItem) {
                return (PeatItem) item;
            }
        }

        return null;
    }
}
