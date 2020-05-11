package org.jurassicraft.server.dinosaur;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.ai.util.MovementType;
import org.jurassicraft.server.entity.dinosaur.AlligatorGarEntity;
import org.jurassicraft.server.food.FoodType;
import org.jurassicraft.server.period.TimePeriod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class AlligatorGarDinosaur extends Dinosaur
{
	@Override
    protected DinosaurMetadata buildMetadata() {
        return new DinosaurMetadata(new ResourceLocation(JurassiCraft.MODID, "alligator_gar"))
                .setEntity(AlligatorGarEntity.class, AlligatorGarEntity::new)
                .setDinosaurType(DinosaurType.PASSIVE)
                .setTimePeriod(TimePeriod.CRETACEOUS)
                .setEggColorMale(0x707B94, 0x3B4963)
                .setEggColorFemale(0x7C775E, 0x4D4A3B)
                .setHealth(3, 10)
                .setFlee(true)
                .setSpeed(0.2, 0.40)
                .setAttackSpeed(1.5)
                .setStrength(0.5, 3)
                .setMaximumAge(this.fromDays(30))
                .setEyeHeight(0.35F, 1.2F)
                .setSizeX(0.1F, 1.1F)
                .setSizeY(0.02F, .4F)
                .setStorage(9)
                .setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)))
                .setSleepTime(SleepTime.NO_SLEEP)
                .setBones("anal_fin", "caudal_fin", "first_dorsal_fin", "pectoral_fin_bones", "pelvic_fin_bones", "second_dorsal_fin", "skull", "spine", "teeth")
                .setHeadCubeName("Head")
                .setScale(.65F, 0.15F)
                .setMaxHerdSize(1)
                .setOffset(0.0F, .7F, 0F)
                .setAttackBias(100.0)
                .setMarineAnimal(true)
                .setMovementType(MovementType.NEAR_SURFACE)
                .setBreeding(true, 1, 3, 15, true, false)
                .setRandomFlock(false)
                .setRecipe(new String[][] {
                        { "", "second_dorsal_fin", "first_dorsal_fin", "" },
                        { "caudal_fin", "spine", "pectoral_fin_bones", "skull" },
                        { "anal_fin", "", "pelvic_fin_bones", "teeth" }
                })
                .setSpawn(5, BiomeDictionary.Type.OCEAN);
    }

    @Override
    public void applyMeatEffect(EntityPlayer player, boolean cooked) {
        if (!cooked) {
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 400, 1));
        }
        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
    }
}
