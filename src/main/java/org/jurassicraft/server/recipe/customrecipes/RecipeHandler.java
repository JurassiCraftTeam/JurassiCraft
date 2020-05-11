package org.jurassicraft.server.recipe.customrecipes;

import org.jurassicraft.JurassiCraft;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=JurassiCraft.MODID)
public class RecipeHandler {
	
    @SubscribeEvent
    public static void onRecipeReg(RegistryEvent.Register<IRecipe> event) {
    	event.getRegistry().registerAll(new RecipeDartTippedPotion().setRegistryName("dart_tipped_potion"));
    }
}
