package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.GrindableItem;

public class GrindableItemSlot extends Slot {
    public GrindableItemSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        GrindableItem grindableItem = GrindableItem.getGrindableItem(stack);
        return grindableItem != null && grindableItem.isGrindable(stack);
    }
}
