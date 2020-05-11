package org.jurassicraft.server.block.plant;

import java.util.Random;
import org.jurassicraft.server.util.GameRuleHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AncientCoralBlock extends AncientPlantBlock {
	
	private static final int DENSITY_PER_AREA = 5;
	private static final int SPREAD_RADIUS = 6;
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

    public AncientCoralBlock() {
        super(Material.WATER);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
        this.setCreativeTab(null);
    }

    private boolean canPlaceBlockOn(Block ground) {
        return ground == Blocks.SAND || ground == Blocks.CLAY || ground == Blocks.GRAVEL || ground == Blocks.DIRT;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block down = worldIn.getBlockState(pos.down()).getBlock();
        Block here = worldIn.getBlockState(pos).getBlock();
        Block up = worldIn.getBlockState(pos.up()).getBlock();

        return this.canPlaceBlockOn(down) && here == Blocks.WATER && up == Blocks.WATER;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        Block down = worldIn.getBlockState(pos.down()).getBlock();
        Block up = worldIn.getBlockState(pos.up()).getBlock();

        return this.canPlaceBlockOn(down) && up == Blocks.WATER;
    }
    
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (GameRuleHandler.PLANT_SPREADING.getBoolean(world)) {

            int light = world.getLight(pos);
            if (light >= 5) {
               

            if (rand.nextInt((15 - light) / 2 + 10) == 0) {

                int i = DENSITY_PER_AREA;


                BlockPos nextPos = null;
                int placementAttempts = 3;

                while (nextPos == null && placementAttempts > 0) {

                    int doubleRadius = SPREAD_RADIUS * 2;
                    BlockPos tmp = pos.add(rand.nextInt(doubleRadius) - SPREAD_RADIUS, -SPREAD_RADIUS,
                            rand.nextInt(doubleRadius) - SPREAD_RADIUS);
                    nextPos = this.findGround(world, tmp);
                    --placementAttempts;
                }

                
                if (nextPos != null) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-SPREAD_RADIUS, -3, -SPREAD_RADIUS), pos.add(SPREAD_RADIUS, 3, SPREAD_RADIUS))) {

                    if (world.getBlockState(blockpos).getBlock() instanceof AncientCoralBlock) {
                        --i;
                        if (i <= 0) {
                            return;
                        }
                    }
                }
                }
                if (nextPos != null) {
                    world.setBlockState(nextPos, this.getDefaultState());
                }
            }
            }
        }
    }

    @Override
	protected BlockPos findGround(World world, BlockPos start) {
        BlockPos pos = start;

        Block down = world.getBlockState(pos.down()).getBlock();
        Block here = world.getBlockState(pos).getBlock();
        Block up = world.getBlockState(pos.up()).getBlock();

        for (int i = 0; i < 8; ++i) {
            if (this.canPlaceBlockOn(down) && here == Blocks.WATER && up == Blocks.WATER) {
                return pos;
            }

            down = here;
            here = up;
            pos = pos.up();
            up = world.getBlockState(pos.up()).getBlock();
        }

        return null;
    }

    @Override
    protected boolean isNearWater(World world, BlockPos nextPos) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
    
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        
        BlockPos beside = pos.offset(face);
        Block blockBeside = world.getBlockState(beside).getBlock();
        
        if (blockBeside instanceof BlockGlass) {
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

    	BlockPos beside = pos.offset(side);
    	IBlockState state = blockAccess.getBlockState(beside);
        Block blockBeside = state.getBlock();
        
        if (blockBeside instanceof BlockGlass) {
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.SOLID;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }
    
    @Override
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.NONE;
    }
    
    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
    	return layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT;
    }
}
