package org.jurassicraft.server.world.structure;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.OrientedBlock;
import org.jurassicraft.server.block.machine.CleaningStationBlock;
import org.jurassicraft.server.world.loot.Loot;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticistVillagerHouse extends StructureVillagePieces.Village {
	
    public static final int WIDTH = 8;
    public static final int HEIGHT = 6;
    public static final int DEPTH = 14;

    public static final IBlockState[] MACHINES = new IBlockState[] { BlockHandler.DNA_EXTRACTOR.getDefaultState(), BlockHandler.DNA_SEQUENCER.getDefaultState(), BlockHandler.DNA_COMBINATOR_HYBRIDIZER.getDefaultState(), BlockHandler.DNA_SYNTHESIZER.getDefaultState(), BlockHandler.EMBRYONIC_MACHINE.getDefaultState() };

    private static final ResourceLocation STRUCTURE = new ResourceLocation(JurassiCraft.MODID, "geneticist_house");

    private int count;
    private EnumFacing coordBaseMode;
    private Mirror mirror;
    private Rotation rotation;

    public GeneticistVillagerHouse() {
    }

    public GeneticistVillagerHouse(StructureVillagePieces.Start start, int type, StructureBoundingBox bounds, EnumFacing facing) {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = bounds;
    }

    @Override
    public void setCoordBaseMode(EnumFacing facing) {
        super.setCoordBaseMode(facing);
        this.coordBaseMode = facing;
        if (facing == null) {
            this.rotation = Rotation.NONE;
            this.mirror = Mirror.NONE;
        } else {
            switch (facing) {
                case SOUTH:
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.NONE;
                    break;
                case WEST:
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;
                case EAST:
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;
                default:
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.NONE;
            }
        }
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox bounds) {
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(this.rotation).setMirror(this.mirror).setIgnoreEntities(true);
        Template template = templateManager.getTemplate(server, STRUCTURE);
        if (this.averageGroundLvl < 0) {
            this.averageGroundLvl = this.getAverageGroundLevel(world, bounds);
            if (this.averageGroundLvl < 0) {
                return false;
            }
            settings.setIgnoreEntities(false);
            this.boundingBox.offset(0, ((this.averageGroundLvl - this.boundingBox.maxY) + HEIGHT) - 1, 0);

        }
        int ox = (this.rotation == Rotation.CLOCKWISE_90 && this.mirror == Mirror.NONE) ? 14 : -1;
		int oz = (this.rotation == Rotation.NONE && this.mirror == Mirror.LEFT_RIGHT) ? 14 : -1;
		BlockPos lowerCorner = new BlockPos(this.boundingBox.minX + ox, this.boundingBox.minY, this.boundingBox.minZ + oz);
		settings.setBoundingBox(new StructureBoundingBox(this.boundingBox.minX + ox, this.boundingBox.minY, this.boundingBox.minZ + oz, this.boundingBox.maxX + ox, this.boundingBox.maxZ, this.boundingBox.maxZ + oz));
        template.addBlocksToWorldChunk(world, lowerCorner, settings);
        this.count++;
        Map<BlockPos, String> dataBlocks = template.getDataBlocks(lowerCorner, settings);
        Map<BlockPos, String> dataBlocksClone = dataBlocks.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        dataBlocks.forEach((pos, type) -> {
        	IBlockState base = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
            switch (type) {
                case "GeneticistChest":
                    world.setBlockState(pos, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, rotate(this.coordBaseMode, this.mirror == Mirror.LEFT_RIGHT ? true : false)));
                    ((TileEntityChest) world.getTileEntity(pos)).setLootTable(Loot.GENETICIST_HOUSE_CHEST, random.nextLong());
                    dataBlocksClone.remove(pos);
                    break;
                case "GeneticistMachine":
                    if (random.nextInt(4) == 0) {
                    	IBlockState machine = MACHINES[random.nextInt(MACHINES.length)];
                    	if(machine.getBlock() instanceof OrientedBlock) {
                            world.setBlockState(pos, machine.withProperty(((OrientedBlock) machine.getBlock()).FACING, rotate(this.coordBaseMode, this.mirror == Mirror.LEFT_RIGHT ? true : false)));
                    	}else {
                    		world.setBlockState(pos, machine);
                    	}
                    }
                    dataBlocksClone.remove(pos);
                    break;
                case "Log":
                    world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState()));
                    dataBlocksClone.remove(pos);
                    break;
                case "Base":
                    world.setBlockState(pos, base);
                    this.clearCurrentPositionBlocksUpwards(world, new BlockPos(pos.getX(), pos.getY() + 7, pos.getZ()), bounds);
                    this.replaceAirAndLiquidDownwards(world, base, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), bounds);
                    dataBlocksClone.remove(pos);
                    break;
                case "BaseStairs":
                    world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, this.coordBaseMode)));
                    this.clearCurrentPositionBlocksUpwards(world, new BlockPos(pos.getX(), pos.getY() + 7, pos.getZ()), bounds);
                    this.replaceAirAndLiquidDownwards(world, base, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), bounds);
                    dataBlocksClone.remove(pos);
                    break;
                case "Wall":
                    world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState()));
                    dataBlocksClone.remove(pos);
                    break;
                case "BaseWood":
                    world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState()));
                    this.clearCurrentPositionBlocksUpwards(world, new BlockPos(pos.getX(), pos.getY() + 7, pos.getZ()), bounds);
                    this.replaceAirAndLiquidDownwards(world, base, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), bounds);
                    dataBlocksClone.remove(pos);
                    break;
                case "Hay":
                    world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.HAY_BLOCK.getDefaultState()));
                    this.clearCurrentPositionBlocksUpwards(world, new BlockPos(pos.getX(), pos.getY() + 7, pos.getZ()), bounds);
                    this.replaceAirAndLiquidDownwards(world, base, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), bounds);
                    dataBlocksClone.remove(pos);
                    break;
                case "Fence":
                    world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState()));
                    dataBlocksClone.remove(pos);
                    break;
                case "FenceGate":
                    IBlockState gate;
                    switch (this.structureType) {
                        case 2:
                            gate = Blocks.ACACIA_FENCE_GATE.getDefaultState();
                            break;
                        case 3:
                            gate = Blocks.SPRUCE_FENCE_GATE.getDefaultState();
                            break;
                        default:
                            gate = Blocks.OAK_FENCE_GATE.getDefaultState();
                            break;
                    }
                    world.setBlockState(pos, gate.withRotation(this.rotation).withMirror(this.mirror));
                    dataBlocksClone.remove(pos);
                    break;
                case "StainedClay":
                    IBlockState state = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                    if(state.getPropertyKeys().contains(BlockLog.LOG_AXIS)) {
                        state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
                    }
                    world.setBlockState(pos, state);
                    dataBlocksClone.remove(pos);
                    break;
                case "Bricks":
                    IBlockState brick = Blocks.STONEBRICK.getDefaultState();
                    if (this.structureType == 1) {
                        brick = Blocks.SANDSTONE.getDefaultState();
                    }
                    world.setBlockState(pos, brick);
                    dataBlocksClone.remove(pos);
                    break;
            }
        });

        dataBlocksClone.forEach((pos, type) -> {
            switch (type) {
                case "Door":
                    world.setBlockState(pos, this.biomeDoor().getDefaultState().withProperty(BlockDoor.FACING, this.coordBaseMode));
                    break;
                case "DoorTop":
                    world.setBlockState(pos, this.biomeDoor().getDefaultState().withProperty(BlockDoor.FACING, this.coordBaseMode).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER).withRotation(this.rotation).withMirror(this.mirror));
                    break;
                case "Torch":
                	if(!this.isZombieInfested)
                		world.setBlockState(pos, Blocks.TORCH.getDefaultState());
                    break;
                case "TorchDoor":
                	if(!this.isZombieInfested)
                		world.setBlockState(pos, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode));
                    break;
            }
        });
        dataBlocksClone.clear();
        return true;
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger("count", this.count);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        this.count = tagCompound.getInteger("count");
    }

    public static class CreationHandler implements VillagerRegistry.IVillageCreationHandler {
        @Override
        public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int size) {
            return new StructureVillagePieces.PieceWeight(GeneticistVillagerHouse.class, 1, MathHelper.getInt(random, 0, 1));
        }

        @Override
        public Class<?> getComponentClass() {
            return GeneticistVillagerHouse.class;
        }

        @Override
        public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int minX, int minY, int minZ, EnumFacing facing, int componentType) {
            StructureBoundingBox bounds = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, WIDTH, HEIGHT, DEPTH, facing);
            return StructureComponent.findIntersecting(pieces, bounds) == null ? new GeneticistVillagerHouse(startPiece, componentType, bounds, facing) : null;
        }
    }

	public void clearCurrentPositionBlocksUpwards(World world, BlockPos pos, StructureBoundingBox boundingBox) {
		if (boundingBox.isVecInside(pos)) {
			while (!world.isAirBlock(pos) && pos.getY() < 255) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				pos = pos.up();
			}
		}
	}
	
	public void replaceAirAndLiquidDownwards(World world, IBlockState blockstate, BlockPos pos, StructureBoundingBox boundingBox) {
		if (boundingBox.isVecInside(pos)) {
			while ((world.isAirBlock(pos) || world.getBlockState(pos).getMaterial().isLiquid()) && pos.getY() > 1) {
				world.setBlockState(pos, blockstate, 2);
				pos = pos.down();
			}
		}
	}
    
    public EnumFacing rotate(EnumFacing facing, boolean clockwise)
    {
    	if(!clockwise) {
        switch (facing)
        {
            case NORTH:
                return EnumFacing.EAST;
            case EAST:
                return EnumFacing.SOUTH;
            case SOUTH:
                return EnumFacing.WEST;
            case WEST:
                return EnumFacing.NORTH;
            default:
                throw new IllegalStateException("Rotation failed while generation for: " + facing);
        }
    	}
    	switch (facing)
        {
             case NORTH:
                 return EnumFacing.WEST;
             case EAST:
                 return EnumFacing.NORTH;
             case SOUTH:
                 return EnumFacing.EAST;
             case WEST:
                 return EnumFacing.SOUTH;
             default:
                 throw new IllegalStateException("Rotation failed while generation for: " + facing + " which is clockwise");
        }
    	
    }
}
