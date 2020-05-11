package org.jurassicraft.server.dinosaur;

import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.dinosaur.TriceratopsEntity;
import org.jurassicraft.server.period.TimePeriod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class TriceratopsDinosaur extends Dinosaur {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("Chin", null);
		put("Tail", null);
		put("Horn", null);
		put("Neck", null);
		put("FOOT", null);
		put("Foot", null);
	}};
	
    @Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "triceratops"))
                .setEntity(TriceratopsEntity.class, TriceratopsEntity::new)
                .setDinosaurType(DinosaurType.NEUTRAL)
                .setTimePeriod(TimePeriod.CRETACEOUS)
                .setEggColorMale(0x404138, 0x1C1C1C)
                .setEggColorFemale(0x8F7B76, 0x73676A)
                .setSpeed(0.3, 0.35)
                .setAttackSpeed(1.3)
                .setHealth(10, 70)
                .setStrength(2, 10)
                .setMaximumAge(this.fromDays(45))
                .setEyeHeight(0.45F, 1.8F)
                .setSizeX(0.35F, 2.5F)
                .setSizeY(0.6F, 3.0F)
                .setStorage(36)
                .setDiet(Diet.HERBIVORE.get())
                .setBones("front_leg_bones", "hind_leg_bones", "horn", "neck_vertebrae", "pelvis", "ribcage", "shoulder_bone", "skull", "tail_vertebrae", "tooth")
                .setHeadCubeName("Head")
                .setScale(1.35F, 0.325F)
                .setOffset(0.0F, 0.45F, 0.0F)
                .setImprintable(true)
                .setDefendOwner(true)
                .setAttackBias(400.0)
                .setBreeding(false, 2, 6, 48, false, true)
                .setSpawn(10, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.FOREST)
                .setOverlays(OverlayType.EYELID)
                .setOffsetCubes(offsets)
                .setRecipe(new String[][] {
                        { "", "", "", "", "horn" },
                        { "tail_vertebrae", "pelvis", "ribcage", "neck_vertebrae", "skull" },
                        { "hind_leg_bones", "hind_leg_bones", "", "shoulder_bone", "tooth" },
                        { "", "", "", "front_leg_bones", "front_leg_bones" } }
                );
    }
}
