package org.jurassicraft.server.plugin.jei.category.dnaextractor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jurassicraft.server.api.ExtractableItem;

public class ExtractorInput {
    public final ItemStack stack;
    public final ExtractableItem extract;

    public ExtractorInput(ItemStack stack) {
        this.stack = stack;
        Item item = stack.getItem();
        if(item instanceof ItemBlock) {
        	this.extract = (ExtractableItem)((ItemBlock)stack.getItem()).getBlock();
        } else {
        	this.extract = (ExtractableItem)stack.getItem();
        }
    }
}
