package org.jurassicraft.server.block.plant;

import java.util.Random;

import org.jurassicraft.server.item.ItemHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Codyr on 11/11/2016.
 */
public class HippuritesRadiosusBlock extends AncientCoralBlock {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
        return BOUNDS;
    }
    
    @Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemHandler.HIPPURITES_RADIOSUS;
	}
    
    @Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

		return new ItemStack(ItemHandler.HIPPURITES_RADIOSUS);
	}
}
