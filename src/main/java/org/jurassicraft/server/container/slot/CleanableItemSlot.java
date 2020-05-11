package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.CleanableItem;

public class CleanableItemSlot extends Slot {
    public CleanableItemSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        CleanableItem cleanableItem = CleanableItem.getCleanableItem(stack);
        return cleanableItem != null && cleanableItem.isCleanable(stack);
    }
}
