package org.jurassicraft.server.tab;

import org.jurassicraft.server.item.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class JurassiCraftVehiclesTab extends CreativeTabs {

    public JurassiCraftVehiclesTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemHandler.VEHICLE_ITEM, 1, 1);
    }
}
