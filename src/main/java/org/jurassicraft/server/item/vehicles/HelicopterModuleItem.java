package org.jurassicraft.server.item.vehicles;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jurassicraft.server.tab.TabHandler;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
/*
public class HelicopterModuleItem extends Item {
    private final Class<? extends HelicopterModule> module;
    private final String moduleID;

    public HelicopterModuleItem(String helicopterModuleID) {
        this.moduleID = helicopterModuleID;
        this.module = checkNotNull(HelicopterModule.registry.get(helicopterModuleID), "Invalid module id " + helicopterModuleID);
        this.setCreativeTab(TabHandler.ITEMS);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag tooltipFlag) {
        super.addInformation(stack, worldIn, tooltip, tooltipFlag);
        tooltip.add("Right click on a helicopter to attach this module");
    }

    public Class<? extends HelicopterModule> getModule() {
        return this.module;
    }

    public String getModuleID() {
        return this.moduleID;
    }
}*/
