package org.jurassicraft.server.conf;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.proxy.ServerProxy;
import org.jurassicraft.server.world.structure.StructureGenerationHandler;

@Config(modid = JurassiCraft.MODID, category = "")
@Mod.EventBusSubscriber(modid = JurassiCraft.MODID)
public class JurassiCraftConfig { //TODO: move all structures to same parent package

    @Config.Name("entities")
    public static final Entities ENTITIES = new Entities();

    @Config.Name("mineral Generation")
    public static final MineralGeneration MINERAL_GENERATION = new MineralGeneration();

    @Config.Name("plant Generation")
    public static final PlantGeneration PLANT_GENERATION = new PlantGeneration();

    @Config.Name("structure Generation")
    public static final StructureGeneration STRUCTURE_GENERATION = new StructureGeneration();

    @Config.Name("items")
    public static final Items ITEMS = new Items();
    
    @Config.Name("vehicles")
    public static final Vehicles VEHICLES = new Vehicles();


    public static class Entities {
        @Config.Name("Dinosaur Spawning")
        public boolean naturalSpawning_D = false;
        
        @Config.Name("Goat Spawning")
        public boolean naturalSpawning_G = false;

        @Config.Name("Only Hunt when Hungry")
        public boolean huntWhenHungry = false;

        @Config.Name("Allow Carcass Spawning")
        public boolean allowCarcass = true;

    }

    public static class MineralGeneration {
        @Config.Name("Fossil Generation")
        public boolean fossilGeneration = true;

        @Config.Name("Nest Fossil Generation")
        public boolean nestFossilGeneration = true;

        @Config.Name("Fossilized Trackway Generation")
        public boolean trackwayGeneration = true;

        @Config.Name("Plant Fossil Generation")
        public boolean plantFossilGeneration = true;

        @Config.Name("Amber Generation")
        public boolean amberGeneration = true;

        @Config.Name("Ice Shard Generation")
        public boolean iceShardGeneration = true;

        @Config.Name("Gypsum Generation")
        public boolean gypsumGeneration = true;

        @Config.Name("Petrified Tree Generation")
        public boolean petrifiedTreeGeneration = true;
    }

    public static class PlantGeneration {
        @Config.Name("Moss Generation")
        public boolean mossGeneration = true;

        @Config.Name("Peat Generation")
        public boolean peatGeneration = true;

        @Config.Name("Flower Generation")
        public boolean flowerGeneration = true;

        @Config.Name("Gracilaria Generation")
        public boolean gracilariaGeneration = true;

        @Config.Name("Peat Spread Speed")
        @Config.Comment("Lower is faster, Higher is slower!")
        public int peatGenerationSpeed = 20;
    }

    public static class StructureGeneration {
        @Config.Name("Visitor Center Generation")
        public boolean visitorcentergeneration = true;

        @Config.Name("Raptor Paddock Generation")
        public boolean raptorgeneration = true;
        
        @Config.Name("Raptor Paddock Rarity")
        @Config.Comment("Tested between 1/50 and 1/infinite (The default rarity is 1/4000)")
        public int paddockRarity = 4000;
    }
    
    public static class Items {
        @Config.Name("Disable Growth Serum")
        public boolean disableGrowthSerum = false;
        
        @Config.Name("Loot Spawnrate (percentage)")
        @Config.Comment("This represents the percentage of loot items in dungeons (restart required)")
        public int lootSpawnrate = 100;
    }

    public static class Vehicles {
        @Config.Name("Helicopter Explosion")
        public boolean helicopterExplosion;
        
        @Config.Name("Helicopter zoom-out")
        @Config.Comment("Experimental: This is still incompatible with Optifine")
        public boolean helicopterZoomout = false;

        @Config.Name("Enable Tour Rail Blocks")
        public boolean tourRailBlockEnabled = true;
        
        @Config.Name("Vehicles destroying blocks")
        public boolean destroyBlocks = true;
    }
    
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(JurassiCraft.MODID.equals(event.getModID())) {
            ConfigManager.sync(JurassiCraft.MODID, Config.Type.INSTANCE);
            StructureGenerationHandler.reloadGenerators();
            EntityHandler.reinitSpawns();
            ServerProxy.reinitSpawns();
        }
    }
}
