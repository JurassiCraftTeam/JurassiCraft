package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import org.jurassicraft.server.dinosaur.Dinosaur.BirthType;
import org.jurassicraft.server.item.SyringeItem;

public class SyringeSlot extends Slot {
    public SyringeSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
    	return stack.getItem() instanceof SyringeItem && SyringeItem.getDinosaur(stack).getMetadata().getBirthType() == BirthType.EGG_LAYING;
    }
}
