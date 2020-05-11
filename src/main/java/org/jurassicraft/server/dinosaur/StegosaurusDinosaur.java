package org.jurassicraft.server.dinosaur;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.dinosaur.StegosaurusEntity;
import org.jurassicraft.server.period.TimePeriod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class StegosaurusDinosaur extends Dinosaur{
	
	@Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "stegosaurus"))
                .setEntity(StegosaurusEntity.class, StegosaurusEntity::new)
                .setDinosaurType(DinosaurType.NEUTRAL)
                .setTimePeriod(TimePeriod.JURASSIC)
                .setEggColorMale(0x404138, 0x1C1C1C)
                .setEggColorFemale(0x8F7B76, 0x73676A)
                .setSpeed(0.3, 0.35)
                .setAttackSpeed(1.3)
                .setHealth(10, 70)
                .setStrength(2, 10)
                .setMaximumAge(this.fromDays(45))
                .setEyeHeight(0.45F, 1.8F)
                .setSizeX(3.0F, 4.0F)
                .setSizeY(2.0F, 3.5F)
                .setStorage(36)
                .setDiet(Diet.HERBIVORE.get())
                .setBones("front_leg_bones", "hind_leg_bones", "horn", "neck_vertebrae", "pelvis", "ribcage", "shoulder_bone", "skull", "tail_vertebrae", "tooth")
                .setHeadCubeName("Head")
                .setScale(1.35F, 0.325F)
                .setOffset(0.0F, 0.45F, 0.0F)
                .setImprintable(true)
                .setDefendOwner(true)
                .setMaxHerdSize(15)
                .setAttackBias(400.0)
                .setBreeding(false, 2, 6, 48, false, true)
                .setSpawn(5, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.FOREST);
    }
}

