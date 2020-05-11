package org.jurassicraft.server.item;

import org.jurassicraft.server.tab.TabHandler;

import net.minecraft.item.ItemFood;

public class FunFriesItem extends ItemFood {
	public FunFriesItem() {
	 super(0, false);
     this.setCreativeTab(TabHandler.FOODS);
	}
}
