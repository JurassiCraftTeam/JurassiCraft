package org.jurassicraft.server.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.dinosaur.GallimimusEntity;
import org.jurassicraft.server.food.FoodType;
import org.jurassicraft.server.period.TimePeriod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class GoatAnimal extends Animal {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("Lower jaw", null);
		put("Cheeks", null);
	}};
	
	@Override
    protected AnimalMetadata buildMetadata() {
		//Currently this only a partial implementation - not modular at all
        return new AnimalMetadata(new ResourceLocation(JurassiCraft.MODID, "goat"))
                .setAnimalEntity(GoatEntity.class, GoatEntity::new)
                .setEggColor(0xEFEDE7, 0x7B3E20)
                .setAnimalOffsetCubes(offsets);
    }
}
