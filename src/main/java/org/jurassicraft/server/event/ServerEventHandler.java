package org.jurassicraft.server.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.FossilizedTrackwayBlock;
import org.jurassicraft.server.block.plant.DoublePlantBlock;
import org.jurassicraft.server.conf.JurassiCraftConfig;
import org.jurassicraft.server.entity.vehicle.VehicleEntity;
import org.jurassicraft.server.entity.villager.VillagerHandler;
import org.jurassicraft.server.entity.villager.ai.EntityAIResearchFossil;
import org.jurassicraft.server.entity.vehicle.HelicopterEntity;
import org.jurassicraft.server.item.ItemHandler;
import org.jurassicraft.server.util.GameRuleHandler;
import org.jurassicraft.server.util.JCBlockVine;
import org.jurassicraft.server.world.WorldGenCoal;
import org.jurassicraft.server.world.loot.Loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ServerEventHandler {
	
	private static final ArrayList<Block> vinesException = new ArrayList<>();
	
	static {
		vinesException.add(BlockHandler.CLEAR_GLASS);
		vinesException.add(BlockHandler.REINFORCED_GLASS);
	}
	
	public static final ArrayList<Block> getVinesExceptions() {
		return vinesException;
	}

    @SubscribeEvent
    public static void onWorldLoad(final WorldEvent.Load event) {
        GameRuleHandler.register(event.getWorld());
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.WorldTickEvent event) {
        if (event.world.getTotalWorldTime() % 20 == 0) {
            WorldServer world = event.world.getMinecraftServer().getWorld(0);
            Chunk[] chunklist = world.getChunkProvider().getLoadedChunks().toArray(new Chunk[0]);
            int randomChunk = ThreadLocalRandom.current().nextInt(0, chunklist.length);

            Random random = new Random();
            int zeroX = chunklist[randomChunk].getPos().getXStart() + random.nextInt(16);
            int zeroZ = chunklist[randomChunk].getPos().getZStart() + random.nextInt(16);
            BlockPos.MutableBlockPos mutualBlockPos;

            if(!(world.getBiomeForCoordsBody(mutualBlockPos = new BlockPos.MutableBlockPos(zeroX, 0, zeroZ)) instanceof BiomeSwamp))
                return;

            for (int i = 255; i > -1; i--) {
                if (world.getBlockState(mutualBlockPos.setPos(zeroX, i, zeroZ)).getBlock() instanceof BlockDirt && world.getBlockState(mutualBlockPos.setPos(zeroX, i + 1, zeroZ)).getBlock() instanceof BlockGrass) {
                    world.setBlockState(mutualBlockPos.setPos(zeroX, i, zeroZ), BlockHandler.PEAT.getDefaultState());
                    break;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void blockRegistry(final RegistryEvent.Register<Block> e) {
    	e.getRegistry().register(BlockHandler.VINES.setRegistryName("minecraft", "vine"));
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void decorate(final DecorateBiomeEvent.Pre event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Random rand = event.getRand();

        Biome biome = world.getBiome(pos);

        BiomeDecorator decorator = biome.decorator;

        if (JurassiCraftConfig.MINERAL_GENERATION.plantFossilGeneration) {
            if (decorator != null && decorator.chunkProviderSettings != null && !(decorator.coalGen instanceof WorldGenCoal)) {
                decorator.coalGen = new WorldGenCoal(Blocks.COAL_ORE.getDefaultState(), decorator.chunkProviderSettings.coalSize);
            }
        }

        if (JurassiCraftConfig.PLANT_GENERATION.mossGeneration) {
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.CONIFEROUS) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
                if (rand.nextInt(8) == 0) {
                    BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);
                    IBlockState state = world.getBlockState(topBlock);
                    if (world.getBlockState(topBlock.down()).isOpaqueCube() && !state.getMaterial().isLiquid() && !(state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND)) {
                        world.setBlockState(topBlock, BlockHandler.MOSS.getDefaultState(), 2 | 16);
                    }
                }
            }
        }

        if (JurassiCraftConfig.PLANT_GENERATION.flowerGeneration) {
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
                if (rand.nextInt(8) == 0) {
                    BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);
                    IBlockState state = world.getBlockState(topBlock);
                    if (world.getBlockState(topBlock.down()).isOpaqueCube() && !state.getMaterial().isLiquid() && !(state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND)) {
                        world.setBlockState(topBlock.up(), BlockHandler.WEST_INDIAN_LILAC.getDefaultState(), 2 | 16);
                        world.setBlockState(topBlock, BlockHandler.WEST_INDIAN_LILAC.getDefaultState().withProperty(DoublePlantBlock.HALF, DoublePlantBlock.BlockHalf.LOWER), 2 | 16);
                    }
                }
            }
        }

        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
            if (rand.nextInt(8) == 0) {
                BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);
            	IBlockState state = world.getBlockState(topBlock);
                if (world.getBlockState(topBlock.down()).isOpaqueCube() && !state.getMaterial().isLiquid() && !(state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND)) {
                    world.setBlockState(topBlock.up(), BlockHandler.HELICONIA.getDefaultState(), 2 | 16);
                    world.setBlockState(topBlock, BlockHandler.HELICONIA.getDefaultState().withProperty(DoublePlantBlock.HALF, DoublePlantBlock.BlockHalf.LOWER), 2 | 16);
                }
            }
        }

        if (JurassiCraftConfig.PLANT_GENERATION.gracilariaGeneration) {
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN)) {
                if (rand.nextInt(8) == 0) {
                    BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);

                    if (topBlock.getY() < 62) {
                        IBlockState state = world.getBlockState(topBlock.down());

                        if (state.isOpaqueCube()) {
                            world.setBlockState(topBlock, BlockHandler.GRACILARIA.getDefaultState(), 2 | 16);
                        }
                    }
                }
            }
        }

        if (JurassiCraftConfig.PLANT_GENERATION.peatGeneration) {
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
                if (rand.nextInt(2) == 0) {
                    new WorldGenMinable(BlockHandler.PEAT.getDefaultState(), 5, input -> input == Blocks.DIRT.getDefaultState() || input == Blocks.GRASS.getDefaultState()).generate(world, rand, world.getTopSolidOrLiquidBlock(pos));
                }
            }
        }

        if (JurassiCraftConfig.MINERAL_GENERATION.trackwayGeneration) {
            int footprintChance = 20;

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)) {
                footprintChance = 10;
            }

            if (rand.nextInt(footprintChance) == 0) {
                int y = rand.nextInt(20) + 30;

                FossilizedTrackwayBlock.TrackwayType type = FossilizedTrackwayBlock.TrackwayType.values()[rand.nextInt(FossilizedTrackwayBlock.TrackwayType.values().length)];

                for (int i = 0; i < rand.nextInt(2) + 1; i++) {
                    BlockPos basePos = new BlockPos(pos.getX() + rand.nextInt(10) + 3, y, pos.getZ() + rand.nextInt(10) + 3);

                    float angle = (float) (rand.nextDouble() * 360.0F);

                    IBlockState trackway = BlockHandler.FOSSILIZED_TRACKWAY.getDefaultState().withProperty(FossilizedTrackwayBlock.FACING, EnumFacing.fromAngle(angle)).withProperty(FossilizedTrackwayBlock.VARIANT, type);

                    float xOffset = -MathHelper.sin((float) Math.toRadians(angle));
                    float zOffset = MathHelper.cos((float) Math.toRadians(angle));

                    for (int l = 0; l < rand.nextInt(2) + 3; l++) {
                        BlockPos trackwayPos = basePos.add(xOffset * l, 0, zOffset * l);

                        if (world.getBlockState(trackwayPos).getBlock() == Blocks.STONE) {
                            world.setBlockState(trackwayPos, trackway, 2 | 16);
                        }
                    }
                }
            }
        }
    }




    @SubscribeEvent
    public static void onLootTableLoad(final LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        LootTable table = event.getTable();
        Loot.handleTable(table, name);
    }
    @SubscribeEvent
    public static void fall(final LivingFallEvent e){
        e.setCanceled(e.getEntity().getRidingEntity() instanceof HelicopterEntity);
    }
    @SubscribeEvent
    public static void onHarvest(final BlockEvent.HarvestDropsEvent event) {
        IBlockState state = event.getState();
        Random rand = event.getWorld().rand;
        if (rand.nextInt(2) == 0) {
            List<Item> bugs = new ArrayList<>();
            if (state.getBlock() == Blocks.HAY_BLOCK) {
                bugs.add(ItemHandler.COCKROACHES);
                bugs.add(ItemHandler.MEALWORM_BEETLES);
            } else if (state.getBlock() == Blocks.GRASS) {
                if (rand.nextInt(6) == 0) {
                    bugs.add(ItemHandler.CRICKETS);
                }
            } else if (state.getBlock() == Blocks.TALLGRASS) {
                if (rand.nextInt(5) == 0) {
                    bugs.add(ItemHandler.CRICKETS);
                }
            } else if (state.getBlock() == Blocks.PUMPKIN || state.getBlock() == Blocks.MELON_BLOCK) {
                bugs.add(ItemHandler.COCKROACHES);
                bugs.add(ItemHandler.MEALWORM_BEETLES);
            } else if (state.getBlock() == Blocks.COCOA) {
                bugs.add(ItemHandler.COCKROACHES);
                bugs.add(ItemHandler.MEALWORM_BEETLES);
            }
            if (bugs.size() > 0) {
                event.getDrops().add(new ItemStack(bugs.get(rand.nextInt(bugs.size()))));
            }
        }
    }
    
    @SubscribeEvent
	public static void onEntitySpawn(final EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) entity;
			if (villager.getProfessionForge().equals(VillagerHandler.PALEONTOLOGIST)) {
				villager.tasks.addTask(6, new EntityAIResearchFossil(villager, 0.4));
			}
			villager.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SHOVEL));
		}
	}
}
