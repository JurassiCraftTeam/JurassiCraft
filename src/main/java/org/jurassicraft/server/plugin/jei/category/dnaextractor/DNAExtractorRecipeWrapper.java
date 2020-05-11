package org.jurassicraft.server.plugin.jei.category.dnaextractor;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.jurassicraft.server.item.ItemHandler;

@SideOnly(Side.CLIENT)
public class DNAExtractorRecipeWrapper implements IRecipeWrapper {
    private final ExtractorInput input;

    public DNAExtractorRecipeWrapper(ExtractorInput input) {
        this.input = input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
    	
        ingredients.setInputs(ItemStack.class, Lists.newArrayList(input.stack, new ItemStack(ItemHandler.STORAGE_DISC)));
        List<ItemStack> list = Lists.newArrayList();
        list.add(input.extract.getExtractOutput(input.stack, new Random()));
        List<List<ItemStack>> outputs = new ArrayList<>();
        outputs.add(list);
        ingredients.setOutputLists(ItemStack.class, outputs);
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
