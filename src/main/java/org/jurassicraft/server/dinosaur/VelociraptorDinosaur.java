package org.jurassicraft.server.dinosaur;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import java.util.HashMap;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.dinosaur.VelociraptorEntity;
import org.jurassicraft.server.period.TimePeriod;

public class VelociraptorDinosaur extends Dinosaur {
	
	private static final HashMap<String, Float> offsets = new HashMap<String, Float>() {{
		put("Snout 3", null);
		put("neck", null);
		put("tail", null);
		put("claw", null);
	}};
	
	@Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "velociraptor"))
                .setEntity(VelociraptorEntity.class, VelociraptorEntity::new)
                .setDinosaurType(DinosaurType.AGGRESSIVE)
                .setTimePeriod(TimePeriod.CRETACEOUS)
                .setEggColorMale(0xA06238, 0x632D11)
                .setEggColorFemale(0x91765D, 0x5A4739)
                .setSpeed(0.2, 0.3)
                .setAttackSpeed(1.25)
                .setHealth(10, 35)
                .setStrength(1, 8)
                .setMaximumAge(this.fromDays(45))
                .setEyeHeight(0.45F, 1.7F)
                .setSizeX(0.5F, 1.0F)
                .setSizeY(0.5F, 1.8F)
                .setStorage(27)
                .setDiet(Diet.CARNIVORE.get())
                .setSleepTime(SleepTime.DIURNAL)
                .setBones("claw", "tooth", "skull", "foot_bones", "leg_bones", "neck_vertebrae", "ribcage", "shoulder_bone", "tail_vertebrae", "arm_bones")
                .setHeadCubeName("Head")
                .setScale(1.3F, 0.3F)
                .setImprintable(true)
                .setDefendOwner(true)
                .setMaxHerdSize(18)
                .setAttackBias(600.0)
                .setCanClimb(true)
                .setBreeding(false, 1, 7, 28, false, true)
                .setJumpHeight(3)
                .setOffsetCubes(offsets)
                .setSpawn(10, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE)
                .setOverlays(OverlayType.EYELID)
                .setRecipe(new String[][] {
                        { "", "", "neck_vertebrae", "skull" },
                        { "tail_vertebrae", "ribcage", "shoulder_bone", "tooth" },
                        { "leg_bones", "leg_bones", "arm_bones", "claw" },
                        { "foot_bones", "foot_bones", "", "" } }
                );
    }
}
