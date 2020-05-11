package org.jurassicraft.server.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.tab.TabHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClearGlassPaneBlock extends Block {

	public static final PropertyBool BLOCK_NORTH = PropertyBool.create("blocknorth");
	public static final PropertyBool BLOCK_EAST = PropertyBool.create("blockeast");
	public static final PropertyBool BLOCK_SOUTH = PropertyBool.create("blocksouth");
	public static final PropertyBool BLOCK_WEST = PropertyBool.create("blockwest");

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
	private final boolean canDrop;

	protected ClearGlassPaneBlock() {
		super(Material.GLASS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false))
				.withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false)).withProperty(BLOCK_NORTH, Boolean.valueOf(false))
				.withProperty(BLOCK_EAST, Boolean.valueOf(false)).withProperty(BLOCK_SOUTH, Boolean.valueOf(false))
				.withProperty(BLOCK_WEST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)));
		this.canDrop = true;
		this.setSoundType(SoundType.GLASS);
		this.setCreativeTab(TabHandler.BLOCKS);
	}

	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = this.getActualState(state, worldIn, pos);
		}

		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[0]);

		if (((Boolean) state.getValue(NORTH)).booleanValue()
				|| ((Boolean) state.getValue(BLOCK_NORTH)).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.NORTH)]);
		}

		if (((Boolean) state.getValue(SOUTH)).booleanValue()
				|| ((Boolean) state.getValue(BLOCK_SOUTH)).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.SOUTH)]);
		}

		if (((Boolean) state.getValue(EAST)).booleanValue() || ((Boolean) state.getValue(BLOCK_EAST)).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.EAST)]);
		}

		if (((Boolean) state.getValue(WEST)).booleanValue() || ((Boolean) state.getValue(BLOCK_WEST)).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.WEST)]);
		}
	}

	private static int getBoundingBoxIndex(EnumFacing p_185729_0_) {
		return 1 << p_185729_0_.getHorizontalIndex();
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return AABB_BY_INDEX[getBoundingBoxIndex(state)];
	}

	private static int getBoundingBoxIndex(IBlockState state) {
		int i = 0;

		if (((Boolean) state.getValue(NORTH)).booleanValue()
				|| ((Boolean) state.getValue(BLOCK_NORTH)).booleanValue()) {
			i |= getBoundingBoxIndex(EnumFacing.NORTH);
		}

		if (((Boolean) state.getValue(EAST)).booleanValue() || ((Boolean) state.getValue(BLOCK_EAST)).booleanValue()) {
			i |= getBoundingBoxIndex(EnumFacing.EAST);
		}

		if (((Boolean) state.getValue(SOUTH)).booleanValue()
				|| ((Boolean) state.getValue(BLOCK_SOUTH)).booleanValue()) {
			i |= getBoundingBoxIndex(EnumFacing.SOUTH);
		}

		if (((Boolean) state.getValue(WEST)).booleanValue() || ((Boolean) state.getValue(BLOCK_WEST)).booleanValue()) {
			i |= getBoundingBoxIndex(EnumFacing.WEST);
		}

		return i;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		boolean north, east, south, west, blockNorth, blockEast, blockSouth, blockWest;
		north = canPaneConnectToPane(worldIn, pos, EnumFacing.NORTH);
		east = canPaneConnectToPane(worldIn, pos, EnumFacing.EAST);
		south = canPaneConnectToPane(worldIn, pos, EnumFacing.SOUTH);
		west = canPaneConnectToPane(worldIn, pos, EnumFacing.WEST);

		blockNorth = canPaneConnectToBlock(worldIn, pos, EnumFacing.NORTH);
		blockEast = canPaneConnectToBlock(worldIn, pos, EnumFacing.EAST);
		blockSouth = canPaneConnectToBlock(worldIn, pos, EnumFacing.SOUTH);
		blockWest = canPaneConnectToBlock(worldIn, pos, EnumFacing.WEST);

		if (north) {
			blockNorth = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.NORTH);
		} else if (blockNorth) {
//			north = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.NORTH);
		}

		if (east) {
			blockEast = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.EAST);
		} else if (blockEast) {
//			east = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.EAST);
		}

		if (south) {
			blockSouth = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.SOUTH);
		} else if (blockSouth) {
//			south = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.SOUTH);
		}

		if (west) {
			blockWest = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.WEST);
		} else if (blockWest) {
//			west = !canPaneConnectToPaneUP(worldIn, pos, EnumFacing.WEST);
		}

		return state.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(WEST, west)
				.withProperty(EAST, east).withProperty(UP, canPaneConnectToPane(worldIn, pos, EnumFacing.UP))
				.withProperty(DOWN, canPaneConnectToPane(worldIn, pos, EnumFacing.DOWN))
				.withProperty(BLOCK_NORTH, blockNorth).withProperty(BLOCK_SOUTH, blockSouth)
				.withProperty(BLOCK_WEST, blockWest).withProperty(BLOCK_EAST, blockEast);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return !this.canDrop ? Items.AIR : super.getItemDropped(state, rand, fortune);
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false
				: super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	public final boolean attachesTo(IBlockAccess p_193393_1_, IBlockState state, BlockPos pos, EnumFacing facing) {
		Block block = state.getBlock();
		BlockFaceShape blockfaceshape = state.getBlockFaceShape(p_193393_1_, pos, facing);
		return !isExcepBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID
				|| blockfaceshape == BlockFaceShape.MIDDLE_POLE_THIN;
	}

	protected static boolean isExcepBlockForAttachWithPiston(Block p_193394_0_) {
		return p_193394_0_ instanceof BlockShulkerBox || p_193394_0_ instanceof BlockLeaves
				|| p_193394_0_ == Blocks.BEACON || p_193394_0_ == Blocks.CAULDRON || p_193394_0_ == Blocks.GLOWSTONE
				|| p_193394_0_ == Blocks.ICE || p_193394_0_ == Blocks.SEA_LANTERN || p_193394_0_ == Blocks.PISTON
				|| p_193394_0_ == Blocks.STICKY_PISTON || p_193394_0_ == Blocks.PISTON_HEAD
				|| p_193394_0_ == Blocks.MELON_BLOCK || p_193394_0_ == Blocks.PUMPKIN
				|| p_193394_0_ == Blocks.LIT_PUMPKIN || p_193394_0_ == Blocks.BARRIER;
	}

	protected boolean canSilkHarvest() {
		return true;
	}

	/**
	 * Gets the render layer this block will render on. SOLID for solid blocks,
	 * CUTOUT or CUTOUT_MIPPED for on-off transparency (glass, reeds), TRANSLUCENT
	 * for fully blended transparency (stained glass)
	 */
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		// return BlockRenderLayer.CUTOUT_MIPPED;
		return BlockRenderLayer.TRANSLUCENT;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST))
					.withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST))
					.withProperty(BLOCK_NORTH, state.getValue(BLOCK_SOUTH))
					.withProperty(BLOCK_EAST, state.getValue(BLOCK_WEST))
					.withProperty(BLOCK_SOUTH, state.getValue(BLOCK_NORTH))
					.withProperty(BLOCK_WEST, state.getValue(BLOCK_EAST));
		case COUNTERCLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH))
					.withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH))
					.withProperty(BLOCK_NORTH, state.getValue(BLOCK_EAST))
					.withProperty(BLOCK_EAST, state.getValue(BLOCK_SOUTH))
					.withProperty(BLOCK_SOUTH, state.getValue(BLOCK_WEST))
					.withProperty(BLOCK_WEST, state.getValue(BLOCK_NORTH));
		case CLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH))
					.withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH))
					.withProperty(BLOCK_NORTH, state.getValue(BLOCK_WEST))
					.withProperty(BLOCK_EAST, state.getValue(BLOCK_NORTH))
					.withProperty(BLOCK_SOUTH, state.getValue(BLOCK_EAST))
					.withProperty(BLOCK_WEST, state.getValue(BLOCK_SOUTH));
		default:
			return state;
		}
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH))
					.withProperty(BLOCK_NORTH, state.getValue(BLOCK_SOUTH))
					.withProperty(BLOCK_SOUTH, state.getValue(BLOCK_NORTH));
		case FRONT_BACK:
			return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST))
					.withProperty(BLOCK_EAST, state.getValue(BLOCK_WEST))
					.withProperty(BLOCK_WEST, state.getValue(BLOCK_EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, WEST, SOUTH, UP, DOWN, BLOCK_NORTH,
				BLOCK_EAST, BLOCK_WEST, BLOCK_SOUTH });
	}

	/*
	 * ======================================== FORGE START
	 * ========================================
	 */

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos offset = pos.offset(facing);
		return attachesTo(world, world.getBlockState(offset), offset, facing.getOpposite());
	}

	public boolean canPaneConnectToPane(IBlockAccess world, BlockPos pos, EnumFacing dir) {
		BlockPos other = pos.offset(dir);
		IBlockState state = world.getBlockState(other);
		return (state.getBlock().canBeConnectedTo(world, other, dir.getOpposite())
				|| attachesTo(world, state, other, dir.getOpposite()))
				&& (state.getBlock() instanceof BlockPane || state.getBlock() instanceof ClearGlassPaneBlock);
	}

	public boolean canPaneConnectToPaneUP(IBlockAccess world, BlockPos pos, EnumFacing dir) {
		BlockPos other = pos.offset(dir).add(0, 1, 0);
		IBlockState state = world.getBlockState(other);
		return (!(state.getBlock() instanceof BlockAir));
	}

	public boolean canPaneConnectToPaneDOWN(IBlockAccess world, BlockPos pos, EnumFacing dir) {
		BlockPos other = pos.offset(dir).add(0, -1, 0);
		IBlockState state = world.getBlockState(other);
		return (!(state.getBlock() instanceof BlockAir));
	}

	public boolean canPaneConnectToBlock(IBlockAccess world, BlockPos pos, EnumFacing dir) {
		BlockPos other = pos.offset(dir);
		IBlockState state = world.getBlockState(other);
		return (state.getBlock().canBeConnectedTo(world, other, dir.getOpposite())
				|| attachesTo(world, state, other, dir.getOpposite()))
				&& !(state.getBlock() instanceof BlockPane || state.getBlock() instanceof ClearGlassPaneBlock);
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.MIDDLE_POLE_THIN;
	}

}
