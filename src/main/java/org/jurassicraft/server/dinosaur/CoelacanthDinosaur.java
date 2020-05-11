package org.jurassicraft.server.dinosaur;

import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.ai.util.MovementType;
import org.jurassicraft.server.entity.dinosaur.CoelacanthEntity;
import org.jurassicraft.server.food.FoodType;
import org.jurassicraft.server.period.TimePeriod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class CoelacanthDinosaur extends Dinosaur {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("Body Section", null);
		put("Lower jaw rear", null);
		put("Bone1", null);
	}};
	
	@Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "coelacanth"))
                .setEntity(CoelacanthEntity.class, CoelacanthEntity::new)
                .setDinosaurType(DinosaurType.PASSIVE)
                .setTimePeriod(TimePeriod.DEVONIAN)
                .setEggColorMale(0x707B94, 0x3B4963)
                .setEggColorFemale(0x7C775E, 0x4D4A3B)
                .setHealth(3, 10)
                .setFlee(true)
                .setSpeed(0.05, 0.4)
                .setAttackSpeed(1.5)
                .setStrength(0.5, 3)
                .setMaximumAge(this.fromDays(30))
                .setEyeHeight(0.35F, 1.8F)
                .setSizeX(0.1F, 1.0F)
                .setSizeY(0.1F, 1.0F)
                .setStorage(9)
                .setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)))
                .setSleepTime(SleepTime.NO_SLEEP)
                .setBirthType(BirthType.LIVE_BIRTH)
                .setBones("anal_fin", "caudal_fin", "first_dorsal_fin", "pectoral_fin_bones", "pelvic_fin_bones", "second_dorsal_fin", "skull", "spine", "teeth")
                .setHeadCubeName("Head")
                .setScale(1.8F, 0.22F)
                .setMaxHerdSize(1)
                .setOffset(0.0F, 1.1F, -0.2F)
                .setAttackBias(100.0)
                .setMarineAnimal(true)
                .setMovementType(MovementType.DEEP_WATER)
                .setBreeding(true, 1, 3, 15, true, false)
                .setRandomFlock(false)
                .setOffsetCubes(offsets)
                .setRecipe(new String[][] {
                        { "", "second_dorsal_fin", "first_dorsal_fin", "" },
                        { "caudal_fin", "spine", "pectoral_fin_bones", "skull" },
                        { "anal_fin", "", "pelvic_fin_bones", "teeth" }
                })
                .setSpawn(5, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.RIVER);
    }
}
