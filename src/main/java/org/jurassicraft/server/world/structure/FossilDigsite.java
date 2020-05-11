package org.jurassicraft.server.world.structure;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
import org.jurassicraft.server.block.FossilBlock;
import org.jurassicraft.server.block.entity.AncientItemHoldingBlockEntity;
import org.jurassicraft.server.entity.villager.VillagerHandler;
import org.jurassicraft.server.entity.villager.ai.EntityAIResearchFossil;
import org.jurassicraft.server.world.loot.Loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class FossilDigsite extends StructureVillagePieces.Village {

	private int count;
	private EnumFacing coordBaseMode;
	private Mirror mirror;
	private Rotation rotation;
	private int structureType;
	private int villagerCount = 0;

	public FossilDigsite() {
	}

	public FossilDigsite(StructureVillagePieces.Start start, Random random, int type, StructureBoundingBox bounds, EnumFacing facing, int structureType) {
		super(start, type);
		this.setCoordBaseMode(facing);
		this.boundingBox = bounds;
		this.structureType = structureType;
	}

	private static ResourceLocation getResource(final int type) {
		
		return new ResourceLocation(JurassiCraft.MODID, "fossildigsite_" + type);	
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
		PlacementSettings settings = new PlacementSettings().setRotation(this.rotation).setMirror(this.mirror).setIgnoreEntities(true);
		if (this.averageGroundLvl < 0) {
			this.averageGroundLvl = this.getAverageGroundLevel(world, bounds);
			if (this.averageGroundLvl < 0) {
				return true;
			}
		}
		this.boundingBox.offset(0, ((this.averageGroundLvl - this.boundingBox.maxY) + /*height*/13), 0);

		if (structureType == 3) {
			BlockPos offset3 = Template.transformedBlockPos(settings, new BlockPos(0, -12, 0));
			this.boundingBox.offset(offset3.getX(), offset3.getY(), offset3.getZ());
		} else if (structureType == 1) {
			BlockPos offset1 = Template.transformedBlockPos(settings, new BlockPos(0, -6, 0));
			this.boundingBox.offset(offset1.getX(), offset1.getY(), offset1.getZ());
		} else if(structureType == 2){
			BlockPos offset2 = Template.transformedBlockPos(settings, new BlockPos(0, -9, 0));
			this.boundingBox.offset(offset2.getX(), offset2.getY(), offset2.getZ());
		}

		MinecraftServer server = world.getMinecraftServer();
		TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
		Template template = templateManager.getTemplate(server, getResource(this.structureType));
		
		int sizeRot = (this.structureType == 3) ? 8 : this.structureType == 2 ? 7 : 6;

		int ox = (this.rotation == Rotation.CLOCKWISE_90 && this.mirror == Mirror.NONE) ? sizeRot : 0;
		int oz = (this.rotation == Rotation.NONE && this.mirror == Mirror.LEFT_RIGHT) ? sizeRot : 0;
		BlockPos lowerCorner = new BlockPos(this.boundingBox.minX + ox, this.boundingBox.minY, this.boundingBox.minZ + oz);
		if(lowerCorner.getY() <= 0)
			return false;
		settings.setBoundingBox(new StructureBoundingBox(this.boundingBox.minX + ox, this.boundingBox.minY, this.boundingBox.minZ + oz, this.boundingBox.maxX + ox, this.boundingBox.maxZ, this.boundingBox.maxZ + oz));

		Map<BlockPos, String> dataBlocks2 = template.getDataBlocks(lowerCorner, settings);
		HashMap<BlockPos, IBlockState> defaultBlocks = getWorldDefaultBlocks(dataBlocks2, world);
		template.addBlocksToWorldChunk(world, lowerCorner, settings);
		spawnPaleontologist(world, random);
		Map<BlockPos, String> dataBlocks = template.getDataBlocks(lowerCorner, settings);
		Map<BlockPos, String> dataBlocksClone = dataBlocks.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		setDynamicBlocks(dataBlocks, dataBlocksClone, world, random, defaultBlocks);
		this.count++;
		return true;
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		super.writeStructureToNBT(tagCompound);
		tagCompound.setInteger("count", this.count);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager) {
		super.readStructureFromNBT(tagCompound, templateManager);
		this.count = tagCompound.getInteger("count");
	}

	private void spawnPaleontologist(World world, Random random) {
		if (random.nextInt(2) == 0) {
			if (this.villagerCount < 2) {
				Entity paleontologist = null;
				if(this.isZombieInfested) {
					paleontologist = new EntityZombieVillager(world);
					((EntityZombieVillager) paleontologist).setProfession(VillagerRegistry.getId(VillagerHandler.PALEONTOLOGIST));
				}else {
					paleontologist = new EntityVillager(world, VillagerRegistry.getId(VillagerHandler.PALEONTOLOGIST));
				}
				
				paleontologist.setPosition(boundingBox.minX + (boundingBox.maxX - boundingBox.minX) / 2, boundingBox.minY + 3, boundingBox.minZ + (boundingBox.maxZ - boundingBox.minZ) / 2);
				
				world.spawnEntity(paleontologist);
				this.villagerCount++;
			}
		}
	}

	private HashMap<BlockPos, IBlockState> getWorldDefaultBlocks(Map<BlockPos, String> dataBlocks, World world) {
		
		HashMap<BlockPos, IBlockState> defaultBlocks = new HashMap<BlockPos, IBlockState>();
		dataBlocks.forEach((pos, type) -> {
			switch (type) {
			case "Fossile2":
				defaultBlocks.put(pos, world.getBlockState(pos));
				break;
			}
		});
		return defaultBlocks;
	}

	private void setDynamicBlocks(Map<BlockPos, String> dataBlocks, Map<BlockPos, String> dataBlocksClone, World world, Random random, HashMap<BlockPos, IBlockState> defaultBlocks) {
		int dinoType = random.nextInt(FossilBlock.VARIANT.getAllowedValues().size());
		dataBlocks.forEach((pos, type) -> {
			
			int spawnFossile = 0;
			switch (type) {
			case "Stair":
				BlockPos stair = pos.down();
				world.setBlockState(stair, this.getBiomeSpecificBlockState(world.getBlockState(stair)));
				world.setBlockToAir(pos);
				dataBlocksClone.remove(pos);
				break;
			case "Chest":
				((TileEntityChest) world.getTileEntity(pos.down())).setLootTable(Loot.FOSSIL_DIGSITE_LOOT, random.nextLong());
				world.setBlockToAir(pos);
				dataBlocksClone.remove(pos);
				break;
			case "Log":
				if (this.rotation == Rotation.CLOCKWISE_90) {
					world.setBlockState(pos, this.getBiomeSpecificBlockState(
							Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X)));
				} else {
					world.setBlockState(pos, this.getBiomeSpecificBlockState(
							Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z)));
				}
				dataBlocksClone.remove(pos);
				break;
			case "Log2":
				if (this.rotation == Rotation.CLOCKWISE_90) {
					world.setBlockState(pos, this.getBiomeSpecificBlockState(
							Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z)));
				} else {
					world.setBlockState(pos, this.getBiomeSpecificBlockState(
							Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X)));
				}
				dataBlocksClone.remove(pos);
				break;
			case "Log1":
				world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState()));
				dataBlocksClone.remove(pos);
				break;
			case "Fence":
				world.setBlockState(pos, this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState()));
				dataBlocksClone.remove(pos);
				break;
			case "Fossile":
				spawnFossile = random.nextInt(2);
				if (spawnFossile == 0) {
					world.setBlockState(pos, Blocks.SAND.getDefaultState());
				} else {
					world.setBlockState(pos,
							BlockHandler.FOSSILS.get(0).getDefaultState().withProperty(FossilBlock.VARIANT, dinoType));
				}
				dataBlocksClone.remove(pos);
				break;
			case "Fossile2":
				spawnFossile = random.nextInt(2);
				if (!(spawnFossile == 0)) 
					world.setBlockState(pos, BlockHandler.FOSSILS.get(0).getDefaultState().withProperty(FossilBlock.VARIANT, dinoType));
				
				dataBlocksClone.remove(pos);
				break;
			case "Shovel":
				world.setBlockState(pos, BlockHandler.ANCIENT_ITEM_HOLDING_BLOCK.getDefaultState());
				AncientItemHoldingBlockEntity tile = (AncientItemHoldingBlockEntity) world.getTileEntity(pos);
				if (tile != null) {
					int shovelYN = random.nextInt(2);
					int durability = random.nextInt(229) + 20;
					if (shovelYN == 1) {
						tile.setDisplayItemStack(new ItemStack(Items.IRON_SHOVEL, 1, durability));
					} else {
						tile.setDisplayItemStack(new ItemStack(Items.IRON_PICKAXE, 1, durability));
					}
					tile.setDisplayItemYOffset(0.5f);
				}
				dataBlocksClone.remove(pos);
				break;
			
			}
		});
		
		dataBlocksClone.forEach((pos, type) -> {
			switch (type) {
			case "Torch":
				if(!this.isZombieInfested)
					world.setBlockState(pos, Blocks.TORCH.getDefaultState(), 2);
				break;
			}
			
		});
		dataBlocksClone.clear();
	}

	public static class CreationHandler implements VillagerRegistry.IVillageCreationHandler {
		@Override
		public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int size) {
			return new StructureVillagePieces.PieceWeight(FossilDigsite.class, 1, MathHelper.getInt(random, 0, 1));
		}

		@Override
		public Class<?> getComponentClass() {
			return FossilDigsite.class;
		}

		@Override
		public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int minX, int minY, int minZ, EnumFacing facing, int componentType) {
			int randomValue = random.nextInt(100);
			
            int type = -1;
            int width = 0;
            int height = 13;
            int depth = 0;
			if (randomValue < 50) {
				type = 3;
				width = 9;
				depth = 9;
			} else if (randomValue < 80) {
				type = 1;
				width = 8;
				depth = 7;
			} else {
				type = 2;
				width = 8;
				depth = 8;
			}
			StructureBoundingBox bounds = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, width, height, depth, facing);
			return StructureComponent.findIntersecting(pieces, bounds) == null ? new FossilDigsite(startPiece, random, componentType, bounds, facing, type) : null;
		}
	}

}