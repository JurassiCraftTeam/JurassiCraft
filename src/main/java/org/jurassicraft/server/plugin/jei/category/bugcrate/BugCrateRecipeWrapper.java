package org.jurassicraft.server.plugin.jei.category.bugcrate;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jurassicraft.server.item.BugItem;

@SideOnly(Side.CLIENT)
public class BugCrateRecipeWrapper implements IRecipeWrapper {
    private final BugCrateInput input;
    private final ItemStack secondInput;

    public BugCrateRecipeWrapper(BugCrateInput input, ItemStack secondInput) {
        this.input = input;
        this.secondInput = secondInput;
    }
    
    public BugCrateRecipeWrapper(BugCrateInput input) {
        this.input = input;
        this.secondInput = null;
    }

	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.add(this.input.stack);
		inputs.add(this.secondInput);

		ingredients.setInputs(ItemStack.class, inputs);
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		BugItem bug = (BugItem) this.input.stack.getItem();
		int amount = bug.getBreedings(this.secondInput);
		outputs.add(new ItemStack(bug, amount));
		ingredients.setOutputs(ItemStack.class, outputs);
	}

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
