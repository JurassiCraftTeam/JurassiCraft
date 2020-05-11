package org.jurassicraft.server.dinosaur;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.dinosaur.ParasaurolophusEntity;
import org.jurassicraft.server.period.TimePeriod;

public class ParasaurolophusDinosaur extends Dinosaur {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("Calf", null);
		put("Neck", null);
		put("Tail", null);
		put("Snout2", null);
		put("Jaw2", null);
		put("Body2", null);
		put("Hip5(L)", null);
		put("hip4(R)", null);
	}};
	
    @Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "parasaurolophus"))
                .setEntity(ParasaurolophusEntity.class, ParasaurolophusEntity::new)
                .setDinosaurType(DinosaurType.PASSIVE)
                .setTimePeriod(TimePeriod.CRETACEOUS)
                .setEggColorMale(0x9F8138, 0x422306)
                .setEggColorFemale(0x5F653E, 0x3C3F44)
                .setHealth(10, 55)
                .setSpeed(0.35, 0.41)
                .setStrength(2, 8)
                .setMaximumAge(this.fromDays(45))
                .setEyeHeight(0.45F, 2.45F)
                .setSizeX(0.5F, 2.5F)
                .setSizeY(0.8F, 3.5F)
                .setOffset(0.0F, 0.0F, 0.6F)
                .setStorage(36)
                .setDiet(Diet.HERBIVORE.get())
                .setBones("ribcage", "front_leg_bones", "hind_leg_bones", "neck_vertebrae", "pelvis", "shoulder_bone", "skull", "tail_vertebrae", "teeth")
                .setHeadCubeName("Head")
                .setScale(1.6F, 0.4F)
                .setImprintable(true)
                .setFlockSpeed(1.5F)
                .setAttackBias(-100.0)
                .setOffsetCubes(offsets)
                .setBreeding(false, 4, 6, 40, false, true)
                .setRecipe(new String[][] {
                        { "tail_vertebrae", "pelvis", "ribcage", "neck_vertebrae", "skull" },
                        { "hind_leg_bones", "hind_leg_bones", "", "shoulder_bone", "teeth" },
                        { "", "", "", "front_leg_bones", "front_leg_bones" }
                })
                .setOverlays(OverlayType.EYELID)
                .setSpawn(15, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST);
    }
}
