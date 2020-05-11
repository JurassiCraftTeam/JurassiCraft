package org.jurassicraft.server.plugin.jei.category.cleaningstation;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.CleanableItem;

public class CleanableInput {
    public final ItemStack stack;
    public final CleanableItem clean;

    public CleanableInput(ItemStack stack) {
        this.stack = stack;
        Item item = stack.getItem();
        if(item instanceof ItemBlock) {
            this.clean = (CleanableItem)((ItemBlock)stack.getItem()).getBlock();
        } else {
            this.clean = (CleanableItem)stack.getItem();
        }
    }
}
