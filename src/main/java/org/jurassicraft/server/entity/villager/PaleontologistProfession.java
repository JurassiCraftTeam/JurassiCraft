package org.jurassicraft.server.entity.villager;

import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.jurassicraft.JurassiCraft;

public class PaleontologistProfession extends VillagerRegistry.VillagerProfession {
	public PaleontologistProfession() {
		super(JurassiCraft.MODID + ":paleontologist", JurassiCraft.MODID + ":textures/entities/villager/paleontologist.png", JurassiCraft.MODID + ":textures/entities/villager/paleontologist_zombie.png");
		new PaleontologistCareer(this);
	}
}