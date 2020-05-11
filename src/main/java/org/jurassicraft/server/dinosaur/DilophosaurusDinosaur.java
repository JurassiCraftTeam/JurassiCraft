package org.jurassicraft.server.dinosaur;

import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.dinosaur.DilophosaurusEntity;
import org.jurassicraft.server.period.TimePeriod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class DilophosaurusDinosaur extends Dinosaur {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("Snout 2", null);
		put("Snout 3", null);
		put("Neck", null);
		put("Tail", null);
		put("Head", null);
	}};
	
	 @Override
	 protected DinosaurMetadata buildMetadata() {
		 	return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "dilophosaurus"))
	                .setEntity(DilophosaurusEntity.class, DilophosaurusEntity::new)
	                .setDinosaurType(DinosaurType.AGGRESSIVE)
	                .setTimePeriod(TimePeriod.JURASSIC)
	                .setEggColorMale(0x6B7834, 0x2A2E30)
	                .setEggColorFemale(0x62712E, 0x30241C)
	                .setHealth(10, 30)
	                .setSpeed(0.35, 0.40)
	                .setStrength(2, 6)
	                .setMaximumAge(this.fromDays(40))
	                .setEyeHeight(0.35F, 1.8F)
	                .setSizeX(0.4F, 1.2F)
	                .setSizeY(0.5F, 1.9F)
	                .setStorage(27)
	                .setDiet(Diet.CARNIVORE.get())
	                .setSleepTime(SleepTime.CREPUSCULAR)
	                .setBones("skull", "tooth", "arm_bones", "leg_bones", "neck", "pelvis", "ribcage", "shoulder", "tail_vertebrae")
	                .setHeadCubeName("Head")
	                .setScale(0.95F, 0.22F)
	                .setImprintable(true)
	                .setDefendOwner(true)
	                .setMaxHerdSize(10)
	                .setAttackBias(1200.0)
	                .setBreeding(false, 2, 4, 24, false, true)
	                .setOffsetCubes(offsets)
	                .setOverlays(OverlayType.EYELID)
	                .setRecipe(new String[][] {
	                        { "", "", "", "neck", "skull" },
	                        { "tail_vertebrae", "pelvis", "ribcage", "shoulder", "tooth" },
	                        { "leg_bones", "leg_bones", "", "", "arm_bones" }
	                })
	                .setSpawn(10, BiomeDictionary.Type.FOREST);
    }
}