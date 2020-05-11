package org.jurassicraft.server.util;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.event.ServerEventHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JCBlockVine extends BlockVine {

	public JCBlockVine() {
		
		this.setHardness(0.2F);
		this.setSoundType(SoundType.PLANT);
		this.setUnlocalizedName("vine");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BlockVine;
	}
	
	@Override
	public boolean canAttachTo(World p_193395_1_, BlockPos p_193395_2_, EnumFacing p_193395_3_)
    {
        Block block = p_193395_1_.getBlockState(p_193395_2_.up()).getBlock();
        return this.isAcceptableNeighbor(p_193395_1_, p_193395_2_.offset(p_193395_3_.getOpposite()), p_193395_3_) && (block == Blocks.AIR || block == Blocks.VINE || this.isAcceptableNeighbor(p_193395_1_, p_193395_2_.up(), EnumFacing.UP));
    }
	
	private boolean isAcceptableNeighbor(World p_193396_1_, BlockPos p_193396_2_, EnumFacing p_193396_3_)
    {
        IBlockState iblockstate = p_193396_1_.getBlockState(p_193396_2_);
        return iblockstate.getBlockFaceShape(p_193396_1_, p_193396_2_, p_193396_3_) == BlockFaceShape.SOLID && !isExceptBlockForAttaching(iblockstate.getBlock());
    }

    protected static boolean isExceptBlockForAttaching(Block p_193397_0_)
    {
        return p_193397_0_ instanceof BlockShulkerBox || p_193397_0_ == Blocks.BEACON || p_193397_0_ == Blocks.CAULDRON || p_193397_0_ == Blocks.GLASS || p_193397_0_ == Blocks.STAINED_GLASS || p_193397_0_ == Blocks.PISTON || p_193397_0_ == Blocks.STICKY_PISTON || p_193397_0_ == Blocks.PISTON_HEAD || p_193397_0_ == Blocks.TRAPDOOR || ServerEventHandler.getVinesExceptions().contains(p_193397_0_);
    }
    
}
