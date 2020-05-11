package org.jurassicraft.server.plugin.jei.category.moss;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.ExtractableItem;
import org.jurassicraft.server.block.PeatBlock;

public class PeatInput {
    public final ItemStack stack;
    public final PeatItem extract;

    public PeatInput(ItemStack stack) {
        this.stack = stack;
        Item item = stack.getItem();
        if(item instanceof ItemBlock) {
        	this.extract = (PeatItem)((ItemBlock)stack.getItem()).getBlock();
        } else {
        	this.extract = (PeatItem)stack.getItem();
        }
    }
}
