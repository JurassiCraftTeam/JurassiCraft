package org.jurassicraft.server.entity.villager;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.item.FossilItem;
import org.jurassicraft.server.item.ItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaleontologistCareer extends VillagerRegistry.VillagerCareer {

	public PaleontologistCareer(VillagerRegistry.VillagerProfession parent) {
		super(parent, JurassiCraft.MODID + ":paleontologist");

		EntityVillager.ITradeList specialTradeList = (EntityVillager.ITradeList) (merchant, recipeList, random) -> {
			List<MerchantRecipe> possible = new ArrayList<>();
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("pelvis", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(6) + 1), getRandomItemStack("skull", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("tooth", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(5) + 1), getRandomItemStack("tail_vertebrae", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("shoulder", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("ribcage", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(6) + 1), getRandomItemStack("neck_vertebrae", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("hind_leg_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("front_leg_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(5) + 1), getRandomItemStack("anal_fin", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("caudal_fin", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("first_dorsal_fin", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(5) + 1), getRandomItemStack("pectoral_fin_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("pelvic_fin_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("second_dorsal_fin", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("spine", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(3) + 1), getRandomItemStack("teeth", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(5) + 1), getRandomItemStack("arm_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("leg_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("neck", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(6) + 1), getRandomItemStack("foot_bones", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("shoulder_bone", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), getRandomItemStack("horn", random, 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(5) + 1), getRandomItemStack("claw", random, 1)));
			possible.add(new MerchantRecipe(getRandomItemStack("skull", random, 1), new ItemStack(Items.EMERALD, random.nextInt(4) + 1)));
			possible.add(new MerchantRecipe(getRandomItemStack("pelvis", random, 1), new ItemStack(Items.EMERALD, random.nextInt(2) + 1)));
			possible.add(new MerchantRecipe(getRandomItemStack("teeth", random, 1), new ItemStack(Items.EMERALD, random.nextInt(2) + 1)));
			int count = 1;
			if (random.nextInt(5) == 0) {
				count += random.nextInt(2);
			}
			for (int i = 0; i < count; i++) {
				recipeList.add(possible.get(random.nextInt(possible.size())));
			}
		};

		EntityVillager.ITradeList commonTradeList = (EntityVillager.ITradeList) (merchant, recipeList, random) -> {
			List<MerchantRecipe> possible = new ArrayList<>();
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), new ItemStack(Items.IRON_SHOVEL)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(2) + 1), new ItemStack(ItemHandler.PLASTER_AND_BANDAGE, random.nextInt(3) + 1)));
			possible.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), new ItemStack(ItemHandler.PLANT_FOSSIL)));
			int count = 1;
			for (int i = 0; i < count; i++) {
				recipeList.add(possible.get(random.nextInt(possible.size())));
			}
		};

		this.addTrade(1, commonTradeList);
		this.addTrade(2, commonTradeList);
		this.addTrade(3, specialTradeList);
	}

	private ItemStack getRandomItemStack(String type, Random random, int count) {
		ItemStack stack = new ItemStack(ItemHandler.FOSSILS.get(type), count);
		stack.setItemDamage(EntityHandler.getDinosaurId(FossilItem.fossilDinosaurs.get(type).get(random.nextInt(FossilItem.fossilDinosaurs.get(type).size()))));
		return stack;
	}
}