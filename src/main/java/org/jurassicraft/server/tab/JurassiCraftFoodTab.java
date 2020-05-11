package org.jurassicraft.server.tab;

import java.util.List;

import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.ItemHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class JurassiCraftFoodTab extends CreativeTabs {
    private int[] metas;

    public JurassiCraftFoodTab(String label) {
        super(label);

        final List<Dinosaur> registeredDinosaurs = EntityHandler.getRegisteredDinosaurs();
        this.metas = new int[registeredDinosaurs.size()];

        int i = 0;

        for (final Dinosaur dino : registeredDinosaurs) {
            if (dino.shouldRegister()) {
                this.metas[i] = EntityHandler.getDinosaurId(dino);

                i++;
            }
        }
    }


    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemHandler.DINOSAUR_MEAT);
    }
}
