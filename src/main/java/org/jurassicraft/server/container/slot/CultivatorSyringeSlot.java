package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.SyringeItem;

public class CultivatorSyringeSlot extends Slot {
    public CultivatorSyringeSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
    	return stack.getItem() instanceof SyringeItem && SyringeItem.getDinosaur(stack).getMetadata().getBirthType() == Dinosaur.BirthType.LIVE_BIRTH;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}
