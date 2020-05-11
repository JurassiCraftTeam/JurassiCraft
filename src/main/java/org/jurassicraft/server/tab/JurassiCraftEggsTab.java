package org.jurassicraft.server.tab;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.item.DinosaurSpawnEggItem;
import org.jurassicraft.server.item.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JurassiCraftEggsTab extends CreativeTabs {

	private ItemStack[] stacks = null;
	
    public JurassiCraftEggsTab(String label) {
        super(label);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (this.stacks == null) {
        	DinosaurSpawnEggItem item = ItemHandler.SPAWN_EGG;
        	NonNullList<ItemStack> list = NonNullList.create();
        	item.getSubItems(CreativeTabs.SEARCH, list);
        	
            this.stacks = new ItemStack[list.size()];

            int i = 0;

            for (ItemStack stack : list) {
            	  
            	 NBTTagCompound nbt = getNBT(stack);

                 nbt.setInteger("GenderMode", 2);

                 stack.setTagCompound(nbt);


                this.stacks[i] = stack;
                
                i++;
            }
        }
        return this.stacks[(int) ((JurassiCraft.timerTicks / 20) % this.stacks.length)];
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemHandler.SPAWN_EGG);
    }
    
    public static NBTTagCompound getNBT(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        nbt = new NBTTagCompound();
        stack.setTagCompound(nbt);
        return nbt;
    }
}
