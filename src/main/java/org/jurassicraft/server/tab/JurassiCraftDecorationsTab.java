package org.jurassicraft.server.tab;

import java.util.List;

import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.DisplayBlockItem;
import org.jurassicraft.server.item.ItemHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class JurassiCraftDecorationsTab extends CreativeTabs {
    private int[] metas;

    public JurassiCraftDecorationsTab(String label) {
        super(label);

        final List<Dinosaur> registeredDinosaurs = EntityHandler.getRegisteredDinosaurs();
        this.metas = new int[registeredDinosaurs.size()];

        for (int i = 0; i < registeredDinosaurs.size(); i++) {
            this.metas[i] = DisplayBlockItem.getMetadata(EntityHandler.getDinosaurId(registeredDinosaurs.get(i)), false, false);
        }
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemHandler.DISPLAY_BLOCK_ITEM);
    }
}
