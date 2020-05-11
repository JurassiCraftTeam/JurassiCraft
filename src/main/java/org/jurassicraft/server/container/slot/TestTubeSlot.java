package org.jurassicraft.server.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.item.DNAItem;
import org.jurassicraft.server.item.PlantDNAItem;

public class TestTubeSlot extends Slot {
    public TestTubeSlot(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof DNAItem || stack.getItem() instanceof PlantDNAItem;
    }
    
    @Override
    public int getItemStackLimit(ItemStack stack) {
    	return 1;
    }
}
