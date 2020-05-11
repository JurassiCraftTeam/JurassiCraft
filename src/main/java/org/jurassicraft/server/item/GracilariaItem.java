package org.jurassicraft.server.item;

import org.jurassicraft.server.util.LangUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

/**
 * Copyright 2016 Timeless Modding Team
 */
public class GracilariaItem extends Item implements IPlantable {
    private Block seaweedBlock;

    public GracilariaItem(Block crops) {
        this.seaweedBlock = crops;
    }
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    	ItemStack stack = player.getHeldItem(hand);
        // NOTE:  Pos is the block we are placing ON

        // Based on ItemSeeds.
        if (side != EnumFacing.UP || !player.canPlayerEdit(pos.offset(side), side, stack)) {
            return EnumActionResult.PASS;
        } else if (this.seaweedBlock.canPlaceBlockAt(world, pos.up())) {
            world.setBlockState(pos.up(), this.seaweedBlock.getDefaultState());
            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    //  ___ ____  _             _        _     _
    // |_ _|  _ \| | __ _ _ __ | |_ __ _| |__ | | ___
    //  | || |_) | |/ _` | '_ \| __/ _` | '_ \| |/ _ \
    //  | ||  __/| | (_| | | | | || (_| | |_) | |  __/
    // |___|_|   |_|\__,_|_| |_|\__\__,_|_.__/|_|\___|
    /*
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
    	System.out.println(super.getItemStackDisplayName(stack).split("tile.")[1]);
    	return LangUtils.translate("plant." + super.getItemStackDisplayName(stack).split(".")[1].split(".")[0] + ".name");
    	//return LangUtils.translate(super.getItemStackDisplayName(stack).split("_coral")[0] + ".name");
    }*/

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.seaweedBlock.getDefaultState();
    }
}
