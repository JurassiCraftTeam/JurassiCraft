package org.jurassicraft.server.datafixers;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.jurassicraft.JurassiCraft;

import java.util.List;

public class JurassiCraftDataFixers {
	
    private static final int DATAFIXER_VERSION = 5;
    private static final ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(JurassiCraft.MODID, DATAFIXER_VERSION);

    public static void init() {

        List<String> tileEntityList = Lists.newArrayList("tour_rail", "cultivate_bottom", "cleaning_station", "fossil_grinder", "dna_sequencer", "dna_synthesizer", "embryonic_machine", "embryo_calcification_machi", "dna_extractor", "dna_combinator_hybridizer", "incubator", "display_block", "feeder", "bug_crate", "tileentityelectricfence", "tileentityelectricpole", "tileentityelectricbase");
        modFixs.registerFix(FixTypes.BLOCK_ENTITY, new DataFixerFactory(1, nbt -> {
            ResourceLocation teLoc = new ResourceLocation(nbt.getString("id"));
            String path = teLoc.getResourcePath();
            if(teLoc.getResourceDomain().equals("minecraft") && tileEntityList.contains(path)) {
                nbt.setString("id", JurassiCraft.MODID + ":" + path);
            }
            return nbt;
        }));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(2, nbt -> {
        	if("jurassicraft:iron_nugget".equals(nbt.getString("id"))) 
        		nbt.setString("id", "minecraft:iron_nugget");	
            return nbt;
        }));
        
		modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(3, nbt -> {
			if ("jurassicraft:display_block_item".equals(nbt.getString("id"))) {
				int meta = nbt.getShort("Damage");
				int dinosaurID = meta >> 4 & 0xFFFF;
				int gender = meta >> 1 & 7;
				boolean isSkeleton = (meta & 1) == 1;
				nbt.setShort("Damage", (short) (dinosaurID << 9 | 0 << 4 | gender << 1 | (isSkeleton ? 1 : 0)));

			}
			return nbt;
		}));
		
		modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(4, nbt -> {
			if ("jurassicraft:display_block_item".equals(nbt.getString("id"))) {
				int meta = nbt.getShort("Damage");
				int dinosaurID = meta >> 9;
				int gender = (meta >> 1) & 0x3;
				byte skeletonVariant = (byte) ((meta >> 4) & 0xF);
				boolean isSkeleton = (meta & 1) == 1;
				nbt.setShort("Damage", (short) (dinosaurID << 2 | ((gender == 1 ? 1 : 0) << 1) | (isSkeleton ? 1 : 0)));
		    	nbt.setByte("Type", skeletonVariant);

			}
			return nbt;
		}));
		
		modFixs.registerFix(FixTypes.BLOCK_ENTITY, new DataFixerFactory(4, nbt -> {
			if ("jurassicraft:display_block".equals(nbt.getString("id"))) {
				nbt.setBoolean("IsFossile", nbt.getBoolean("IsMale"));
			}
			return nbt;
		}));
		
		modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(4, nbt -> {
			if ("jurassicraft:jeep_wrangler".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:vehicle_item");
				nbt.setShort("Damage", (short) 1);

			}
			return nbt;
		}));
		
		modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(4, nbt -> {
			if ("jurassicraft:helicopter".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:vehicle_item");
				nbt.setShort("Damage", (short) 2);

			}
			return nbt;
		}));
		
		modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(4, nbt -> {
			if ("jurassicraft:ford_explorer".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:vehicle_item");
				nbt.setShort("Damage", (short) 0);

			}
			return nbt;
		}));

        modFixs.registerFix(FixTypes.ENTITY, new DataFixerFactory(2, compound -> {
            if("jurassicraft.mural".equals(compound.getString("id"))) {
                compound.setString("id", "jurassicraft:entities.mural");
            }
            return compound;
        }));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:gracilaria_seaweed".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:gracilaria_coral");

			}
			return nbt;
		}));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:enallhelia".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:enallhelia_coral");

			}
			return nbt;
		}));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:cladochonus".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:cladochonus_coral");

			}
			return nbt;
		}));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:hippurites_radiosus".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:hippurites_radiosus_coral");

			}
			return nbt;
		}));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:lithostrotion".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:lithostrotion_coral");

			}
			return nbt;
		}));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:aulopora".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:aulopora_coral");

			}
			return nbt;
		}));
        
        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DataFixerFactory(5, nbt -> {
			if ("jurassicraft:stylophyllopsis".equals(nbt.getString("id"))) {
				nbt.setString("id", "jurassicraft:stylophyllopsis_coral");

			}
			return nbt;
		}));
    }
}
