package org.jurassicraft.client.render.item;

import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.render.item.models.GuiItemModelWrapper;
import org.jurassicraft.server.item.ItemHandler;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid=JurassiCraft.MODID, value=Side.CLIENT)
public class EventHandler {
    
    public static IBakedModel DART_GUN_GUI;
    
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent event) {
    	DART_GUN_GUI = getModel(new ResourceLocation(JurassiCraft.MODID, "item/dart_gun_gui"), event.getMap());
    }
    
    @SubscribeEvent
    public static void onModelBaked(ModelBakeEvent event) {
        for(final ModelResourceLocation mrl : event.getModelRegistry().getKeys()) {
            if(mrl.getVariant().equals("inventory")) {
            	ResourceLocation location = new ResourceLocation(mrl.getResourceDomain(), mrl.getResourcePath());
            	if(location.equals(ItemHandler.DART_GUN.getRegistryName())) {
            		event.getModelRegistry().putObject(mrl, new GuiItemModelWrapper(event.getModelRegistry().getObject(mrl), DART_GUN_GUI));
            	}
            }
        }
    }

	private static IBakedModel getModel(ResourceLocation resourceLocation, TextureMap map) {
		IModel model;
		try {
			model = ModelLoaderRegistry.getModel(resourceLocation);
		} catch (Exception e) {
			e.printStackTrace();
			model = ModelLoaderRegistry.getMissingModel();
		}
		return model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, map::registerSprite);
	}
}
