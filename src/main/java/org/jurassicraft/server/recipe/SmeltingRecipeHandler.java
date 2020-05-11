package org.jurassicraft.server.recipe;

import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.tree.TreeType;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.ItemHandler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class SmeltingRecipeHandler {
    public static void init() {
    	
        for (final Dinosaur dinosaur : EntityHandler.getRegisteredDinosaurs()) {
            final int id = EntityHandler.getDinosaurId(dinosaur);
            GameRegistry.addSmelting(new ItemStack(ItemHandler.DINOSAUR_MEAT, 1, id), new ItemStack(ItemHandler.DINOSAUR_STEAK, 1, id), 5F);
        }
        
        for (final TreeType type : TreeType.values()) {
            GameRegistry.addSmelting(BlockHandler.ANCIENT_LOGS.get(type), new ItemStack(Items.COAL, 1, 1), 0.15F);
        }
        
        GameRegistry.addSmelting(new ItemStack(BlockHandler.GYPSUM_COBBLESTONE), new ItemStack(BlockHandler.GYPSUM_STONE), 1.5F);
        GameRegistry.addSmelting(new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(ItemHandler.DNA_NUCLEOTIDES), 1.0F);
        GameRegistry.addSmelting(ItemHandler.GRACILARIA, new ItemStack(ItemHandler.LIQUID_AGAR), 0.5F);

        GameRegistry.addSmelting(ItemHandler.OILED_POTATO_STRIPS, new ItemStack(ItemHandler.FUN_FRIES), 0.5F);

        GameRegistry.addSmelting(ItemHandler.WILD_POTATO, new ItemStack(ItemHandler.WILD_POTATO_COOKED), 0.5F);
        GameRegistry.addSmelting(ItemHandler.GOAT_RAW, new ItemStack(ItemHandler.GOAT_COOKED), 0.5F);
    }
}
