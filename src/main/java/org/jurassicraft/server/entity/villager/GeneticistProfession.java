package org.jurassicraft.server.entity.villager;

import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.jurassicraft.JurassiCraft;

public class GeneticistProfession extends VillagerRegistry.VillagerProfession {
    public GeneticistProfession() {
        super(JurassiCraft.MODID + ":geneticist", JurassiCraft.MODID + ":textures/entities/villager/geneticist.png", JurassiCraft.MODID + ":textures/entities/villager/geneticist_zombie.png");
        new GeneticistCareer(this);
    }
}