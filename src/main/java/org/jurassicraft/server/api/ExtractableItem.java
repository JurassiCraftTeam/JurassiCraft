package org.jurassicraft.server.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Random;

public interface ExtractableItem extends JurassicIngredientItem {
	
    static ExtractableItem getExtractableItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();

            if (item instanceof ExtractableItem) {
            	return (ExtractableItem) item;
            }
        }

        return null;
    }

    static boolean isExtractableItem(ItemStack stack) {
        return getExtractableItem(stack) != null;
    }
    
    ItemStack getExtractOutput(ItemStack stack, Random random);
}
