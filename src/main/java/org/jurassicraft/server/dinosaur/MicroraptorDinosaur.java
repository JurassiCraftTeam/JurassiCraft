package org.jurassicraft.server.dinosaur;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.dinosaur.MicroraptorEntity;
import org.jurassicraft.server.food.FoodType;
import org.jurassicraft.server.period.TimePeriod;

public class MicroraptorDinosaur extends Dinosaur {
	
	 @Override
	 protected DinosaurMetadata buildMetadata() {
	        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "microraptor"))
	                .setEntity(MicroraptorEntity.class, MicroraptorEntity::new)
	                .setDinosaurType(DinosaurType.AGGRESSIVE)
	                .setTimePeriod(TimePeriod.CRETACEOUS)
	                .setEggColorMale(0x142146, 0x101625)
	                .setEggColorFemale(0x0E1423, 0x121827)
	                .setSpeed(0.15, 0.20)
	                .setAttackSpeed(1.3)
	                .setHealth(4, 10)
	                .setStrength(0.5, 2)
	                .setMaximumAge(this.fromDays(30))
	                .setFlee(true)
	                .setEyeHeight(0.2F, 0.5F)
	                .setSizeX(0.2F, 0.7F)
	                .setSizeY(0.25F, 0.6F)
	                .setStorage(9)
	                .setDiet(new Diet().withModule(new Diet.DietModule(FoodType.INSECT)).withModule(new Diet.DietModule(FoodType.MEAT)))
	                .setBones("tooth", "arm_bones", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae")
	                .setHeadCubeName("Head")
	                .setScale(0.4F, 0.15F)
	                .setImprintable(true)
	                .setDefendOwner(true)
	                .setMaxHerdSize(16)
	                .setAttackBias(400.0)
	                .setCanClimb(true)
	                .setBreeding(false, 1, 5, 15, false, true)
	                .setJumpHeight(2)
	                .setRandomFlock(false)
	                .setRecipe(new String[][] {
	                        { "", "", "", "neck_vertebrae", "skull" },
	                        { "tail_vertebrae", "pelvis", "ribcage", "shoulder", "tooth" },
	                        { "", "leg_bones", "leg_bones", "arm_bones", "" },
	                        { "", "foot_bones", "foot_bones", "", "" }
	                })
	                .setOverlays(OverlayType.EYELID)
	                .setSpawn(10, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE);
    }
}
