package org.jurassicraft.server.plugin.jei.category.fossilgrinder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.GrindableItem;

public class GrinderInput {
    public final ItemStack stack;
    public final GrindableItem grind;

    public GrinderInput(ItemStack stack) {
    	
        this.stack = stack;
        Item item = stack.getItem();
        if(item instanceof ItemBlock) {
            this.grind = (GrindableItem)((ItemBlock)stack.getItem()).getBlock();
        } else {
            this.grind = (GrindableItem)stack.getItem();
        }
    }
}
