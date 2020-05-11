package org.jurassicraft.server.dinosaur;

import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.GrowthStage;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.dinosaur.BrachiosaurusEntity;
import org.jurassicraft.server.period.TimePeriod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BrachiosaurusDinosaur extends Dinosaur {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("foot", null);
		put("neck", null);
	}};
	
	 @Override
	    protected DinosaurMetadata buildMetadata() {
	        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "brachiosaurus"))
	                .setEntity(BrachiosaurusEntity.class, BrachiosaurusEntity::new)
	                .setDinosaurType(DinosaurType.NEUTRAL)
	                .setTimePeriod(TimePeriod.JURASSIC)
	                .setEggColorMale(0x87987F, 0x607343)
	                .setEggColorFemale(0xAA987D, 0x4F4538)
	                .setHealth(20, 150)
	                .setSpeed(0.2, 0.22)
	                .setStrength(5, 15)
	                .setMaximumAge(this.fromDays(85))
	                .setEyeHeight(2.2F, 18.4F)
	                .setSizeX(0.9F, 6.5F)
	                .setSizeY(1.5F, 7.0F)
	                .setStorage(54)
	                .setDiet(Diet.HERBIVORE.get())
	                .setBones("skull", "tooth", "tail_vertebrae", "shoulder", "ribcage", "pelvis", "neck_vertebrae", "hind_leg_bones", "front_leg_bones")
	                .setHeadCubeName("head")
	                .setScale(2.5F, 0.3F)
	                .setOffset(0.0F, 0.0F, 1.0F)
	                .setAttackBias(1200.0)
	                .setMaxHerdSize(4)
	                .setBreeding(false, 4, 8, 72, true, false)
	                .setOffsetCubes(offsets)
	                .setOverlays(OverlayType.EYELID)
	                .setEyeTime(20)
	                .setRecipe(new String[][] {
	                        { "", "", "", "", "skull" },
	                        { "", "", "", "neck_vertebrae", "tooth" },
	                        { "tail_vertebrae", "pelvis", "ribcage", "shoulder", "" },
	                        { "", "hind_leg_bones", "hind_leg_bones", "front_leg_bones", "front_leg_bones" } }
	                )
	                .setSpawn(5, BiomeDictionary.Type.FOREST);
    }
}
