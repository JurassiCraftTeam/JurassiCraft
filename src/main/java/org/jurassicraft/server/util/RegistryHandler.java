package org.jurassicraft.server.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.item.ItemHandler;

/**
 * This code is lisenced under a "feel free to take this class and use it" license. The only
 * provisions are that the package declaration is different as to not cause conflicts in the JVM
 * and that I retain authorship.<br>
 * EasyRegistry and ClientEasyRegistry are both proxy and event handlers. HardLib may be replaced as
 * the reference to any main mod class that treats these two classes as required in order to work:
 * you do not need to replicate the entirety of the HardLib package.<br>
 * Alternatively, you can take HardLib as a dependency. https://minecraft.curseforge.com/projects/hardlib/
 * @author Draco18s
 *
 */
@Mod.EventBusSubscriber
public class RegistryHandler
{
    private static Set<Block> blocksToReg = new HashSet<>();
    private static Set<Item>  itemsToReg  = new HashSet<>();
    private static List<IForgeRegistryEntry> otherItems = new ArrayList<IForgeRegistryEntry>();
    private static List<SoundEvent> sounds = new ArrayList<SoundEvent>();
    protected static HashMap<Block,Item> blockItems = new HashMap<Block,Item>();
    private static Method CriterionRegister;

    public static void registerBlock(Block block, String registryname) {
        block.setRegistryName(registryname);
        block.setUnlocalizedName(registryname);
        blocksToReg.add(block);
    }

    public static void registerSound(SoundEvent sound, String name)
    {
        sound.setRegistryName(name);
        sounds.add(sound);
    }

    public static void registerBlockWithItem(Block block, String registryname) {
        block.setRegistryName(JurassiCraft.MODID, registryname);
        block.setUnlocalizedName(registryname);
        ItemBlock ib = new ItemBlock(block);
        ib.setRegistryName(JurassiCraft.MODID, registryname);
        blocksToReg.add(block);
        itemsToReg.add(ib);
        blockItems.put(block, ib);
    }

    public static void registerBlockWithCustomItem(Block block, ItemBlock iBlock, String registryname) {
        block.setRegistryName(JurassiCraft.MODID, registryname);
        iBlock.setRegistryName(JurassiCraft.MODID, registryname);
        blocksToReg.add(block);
        itemsToReg.add(iBlock);
        blockItems.put(block, iBlock);
    }

    public static void registerItem(Item item, String registryname) {
        item.setRegistryName(JurassiCraft.MODID, registryname);
        item.setUnlocalizedName(registryname);
        itemsToReg.add(item);
    }

    public static void registerItem(Item item, ResourceLocation registryname, String unlocalized) {
        item.setRegistryName(registryname);
        item.setUnlocalizedName(unlocalized);
        itemsToReg.add(item);
    }

    public <K extends IForgeRegistryEntry<K>> K registerOther(K object) {
        otherItems.add(object);
        return object;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    	event.getRegistry().registerAll(blocksToReg.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(itemsToReg.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        for(IForgeRegistryEntry e : otherItems) {
            if(e instanceof Enchantment) call(event, e);
        }
    }

    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().registerAll(sounds.toArray(new SoundEvent[0]));
    }

    private static <K extends IForgeRegistryEntry<K>, T> void call(RegistryEvent.Register<K> event, T value) {
        event.getRegistry().register((K) value);
    }

    public static <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
        if(CriterionRegister == null) {
            CriterionRegister = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
            CriterionRegister.setAccessible(true);
        }
        try {
            trigger = (ICriterionTrigger<T>) CriterionRegister.invoke(null, trigger);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            JurassiCraft.getLogger().error("Failed to register trigger " + trigger.getId() + "!");
            e.printStackTrace();
        }
        return trigger;
    }

    @SubscribeEvent
    public static void missingMapBlock(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> missing : event.getMappings()) {
            ResourceLocation identifier = new ResourceLocation(missing.key.getResourcePath());
            if (identifier.equals(new ResourceLocation(JurassiCraft.MODID, "action_figure_block"))) {
                missing.remap(BlockHandler.DISPLAY_BLOCK);
            }
        }
    }
    @SubscribeEvent
    public static void missingMapItem(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> missing : event.getMappings()) {
            ResourceLocation identifier = new ResourceLocation(missing.key.getResourcePath());
            if (identifier.equals(new ResourceLocation(JurassiCraft.MODID, "action_figure"))) {
                missing.remap(ItemHandler.DISPLAY_BLOCK_ITEM);
            }
        }
    }
}
