package org.jurassicraft.server.dinosaur;

import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.dinosaur.TyrannosaurusEntity;
import org.jurassicraft.server.period.TimePeriod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class TyrannosaurusDinosaur extends Dinosaur {
        
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
			put("neck1", 0.005F);
			put("neck2", 0.004F);
			put("neck3", 0.003F);
			put("neck4", 0.002F);
			put("neck5", 0.004F);
			put("neck6", 0.002F);
			put("neck7", 0.003F);
			put("waddle", null);
			put("throat", null);
			put("jawLower3Middle", null);
			put("jawLower3Bottom1", null);
	}};
	
    @Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "tyrannosaurus"))
        		.setEntity(TyrannosaurusEntity.class, TyrannosaurusEntity::new)
                .setDinosaurType(DinosaurType.AGGRESSIVE)
                .setTimePeriod(TimePeriod.CRETACEOUS)
                .setEggColorMale(0x4E502C, 0x353731)
                .setEggColorFemale(0xBA997E, 0x7D5D48)
                .setHealth(10, 80)
                .setSpeed(0.35, 0.42)
                .setAttackSpeed(1.1)
                .setStrength(5, 20)
                .setMaximumAge(this.fromDays(60))
                .setEyeHeight(0.6F, 3.8F)
                .setSizeX(0.45F, 3.0F)
                .setSizeY(0.8F, 3.0F)
                .setStorage(54)
                .setDiet(Diet.CARNIVORE.get())
                .setBones("arm_bones", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder_bone", "skull", "tail_vertebrae", "tooth")
                .setHeadCubeName("head")
                .setScale(2.5F, 0.35F)
                .setMaxHerdSize(3)
                .setAttackBias(1000.0)
                .setOffsetCubes(offsets)
                .setBreeding(false, 2, 4, 60, false, true)
                .setSkeletonPoses("idle", "death", "attacking")
                .setSpawn(5, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST)
                .setOverlays(OverlayType.EYELID, OverlayType.EYE, OverlayType.CLAW, OverlayType.MOUTH, OverlayType.NOSTRILS, OverlayType.STRIPES, OverlayType.TEETH, OverlayType.MALE_PARTS)
                .setRecipe(new String[][] {
                    { "", "", "", "neck_vertebrae", "skull" },
                    { "tail_vertebrae", "pelvis", "ribcage", "shoulder_bone", "tooth" },
                    { "", "leg_bones", "leg_bones", "arm_bones", "" },
                    { "", "foot_bones", "foot_bones", "", "" } });
    }
}
