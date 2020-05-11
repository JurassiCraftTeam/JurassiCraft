package org.jurassicraft.server.plugin.jei.category.bugcrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jurassicraft.server.api.BreedableBug;
import org.jurassicraft.server.api.JurassicIngredientItem;
import org.jurassicraft.server.food.FoodHelper;
import org.jurassicraft.server.food.FoodHelper.FoodKey;
import org.jurassicraft.server.item.BugItem;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.item.ItemStack;

public class BugCrateRecipeCreator {

	public static List<BugCrateRecipeWrapper> getAllRecipes() {
		List<BugCrateRecipeWrapper> recipes = new ArrayList<>();
		addBugRecipes(recipes);

		return recipes;
	}

	private static void addBugRecipes(List<BugCrateRecipeWrapper> recipes) {
		List<BugCrateInput> list = getBugs(BugCrateInput::new);

		for (BugCrateInput input : list) {

			List<FoodKey> foods = FoodHelper.getFoods();
			ArrayList<FoodKey> foodList = new ArrayList<FoodKey>();
			foods.stream()
					.filter(key -> ((BugItem) input.stack.getItem()).getBreedings(new ItemStack(key.getItem())) > 0)
					.forEach(key -> foodList.add(key));

			for (FoodKey key : foodList) {
				BugCrateRecipeWrapper recipe = new BugCrateRecipeWrapper(input, new ItemStack(key.getItem()));

				recipes.add(recipe);
			}
		}
	}
	
	protected static <T> List<T> getBugs(Function<ItemStack, T> function) {
        List<T> list = Lists.newArrayList();
        ForgeRegistries.ITEMS.getValuesCollection()
                .stream()
                .map(ItemStack::new)
                .map(BreedableBug::getBug)
                .filter(Objects::nonNull)
                .map(JurassicIngredientItem::getJEIRecipeTypes)
                .forEach(stackList -> list.addAll(stackList.stream().map(function).collect(Collectors.toList())));
        return list;
    }
}
