package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.food.FoodNutrients;

public class NutrientSlot extends Slot {
    public NutrientSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return FoodNutrients.NUTRIENTS.containsKey(stack.getItem());
    }
}
