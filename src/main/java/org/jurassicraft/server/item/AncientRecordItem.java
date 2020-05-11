package org.jurassicraft.server.item;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import org.jurassicraft.server.tab.TabHandler;

public class AncientRecordItem extends ItemRecord {
    public AncientRecordItem(String name, SoundEvent sound) {
        super(name, sound);
        this.setCreativeTab(TabHandler.ITEMS);
    }
}