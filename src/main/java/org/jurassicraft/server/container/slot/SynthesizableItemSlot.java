package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.SynthesizableItem;

public class SynthesizableItemSlot extends Slot {
    public SynthesizableItemSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        SynthesizableItem synthesizableItem = SynthesizableItem.getSynthesizableItem(stack);
        return synthesizableItem != null && synthesizableItem.isSynthesizable(stack);
    }
}
