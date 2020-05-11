package org.jurassicraft.client.sound;

import com.google.common.collect.Lists;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.util.RegistryHandler;

import java.util.List;

public class SoundHandler {
    public static final SoundEvent TROODONS_AND_RAPTORS = create("troodons_and_raptors");
    public static final SoundEvent JURASSICRAFT_THEME = create("jurassicraft_theme");
    public static final SoundEvent DONT_MOVE_A_MUSCLE = create("dont_move_a_muscle");

    public static final SoundEvent STOMP = create("stomp");
    public static final SoundEvent FEEDER = create("feeder");
    public static final SoundEvent CAR_MOVE = create("car_move");

    public static final SoundEvent BRACHIOSAURUS_LIVING = create("brachiosaurus_living");
    public static final SoundEvent BRACHIOSAURUS_HURT = create("brachiosaurus_hurt");
    public static final SoundEvent BRACHIOSAURUS_DEATH = create("brachiosaurus_death");

    public static final SoundEvent PARASAUROLOPHUS_LIVING = create("parasaurolophus_living");
    public static final SoundEvent PARASAUROLOPHUS_CALL = create("parasaurolophus_call");
    public static final SoundEvent PARASAUROLOPHUS_DEATH = create("parasaurolophus_death");
    public static final SoundEvent PARASAUROLOPHUS_HURT = create("parasaurolophus_hurt");

    public static final SoundEvent TRICERATOPS_LIVING = create("triceratops_living");
    public static final SoundEvent TRICERATOPS_DEATH = create("triceratops_death");
    public static final SoundEvent TRICERATOPS_HURT = create("triceratops_hurt");

    public static final SoundEvent DILOPHOSAURUS_LIVING = create("dilophosaurus_living");
    public static final SoundEvent DILOPHOSAURUS_SPIT = create("dilophosaurus_spit");
    public static final SoundEvent DILOPHOSAURUS_HURT = create("dilophosaurus_hurt");
    public static final SoundEvent DILOPHOSAURUS_DEATH = create("dilophosaurus_death");

    public static final SoundEvent GALLIMIMUS_LIVING = create("gallimimus_living");
    public static final SoundEvent GALLIMIMUS_DEATH = create("gallimimus_death");
    public static final SoundEvent GALLIMIMUS_HURT = create("gallimimus_hurt");

    public static final SoundEvent TYRANNOSAURUS_BREATHING = create("tyrannosaurus_breathing");
    public static final SoundEvent TYRANNOSAURUS_DEATH = create("tyrannosaurus_death");
    public static final SoundEvent TYRANNOSAURUS_HURT = create("tyrannosaurus_hurt");
    public static final SoundEvent TYRANNOSAURUS_DRINKING = create("tyrannosaurus_drinking");
    public static final SoundEvent TYRANNOSAURUS_CALL = create("tyrannosaurus_call");
    public static final SoundEvent[] TYRANNOSAURUS_ROAR = createArray("tyrannosaurus_roar1", "tyrannosaurus_roar2", "tyrannosaurus_roar3");
    public static final SoundEvent TYRANNOSAURUS_STOMP = create("tyrannosaurus_stomp");
    public static final SoundEvent TYRANNOSAURUS_LIVING = create("tyrannosaurus_living");
    public static final SoundEvent TYRANNOSAURUS_ATTACK = create("tyrannosaurus_attack");

    public static final SoundEvent VELOCIRAPTOR_LIVING = create("velociraptor_living");
    public static final SoundEvent VELOCIRAPTOR_HURT = create("velociraptor_hurt");
    public static final SoundEvent VELOCIRAPTOR_BREATHING = create("velociraptor_breathing");
    public static final SoundEvent VELOCIRAPTOR_CALL = create("velociraptor_call");
    public static final SoundEvent VELOCIRAPTOR_DEATH = create("velociraptor_death");
    public static final SoundEvent VELOCIRAPTOR_ATTACK = create("velociraptor_attack");

    public static final SoundEvent MICRORAPTOR_LIVING = create("microraptor_living");
    public static final SoundEvent MICRORAPTOR_HURT = create("microraptor_hurt");
    public static final SoundEvent MICRORAPTOR_DEATH = create("microraptor_death");
    public static final SoundEvent MICRORAPTOR_ATTACK = create("microraptor_attack");

    public static final SoundEvent MUSSAURUS_LIVING = create("mussaurus_living");
    public static final SoundEvent MUSSAURUS_HURT = create("mussaurus_hurt");
    public static final SoundEvent MUSSAURUS_DEATH = create("mussaurus_death");
    public static final SoundEvent MUSSAURUS_ATTACK = create("mussaurus_attack");
    public static final SoundEvent MUSSAURUS_MATE_CALL = create("mussaurus_mate_call");

    public static final SoundEvent GOAT_LIVING = create("goat_living");
    public static final SoundEvent GOAT_HURT = create("goat_hurt");
    public static final SoundEvent GOAT_DEATH = create("goat_death");

    public static final SoundEvent FENCE_SHOCK = create("fence_shock");

    private static List<SoundEvent> sounds = Lists.newArrayList();


    public static SoundEvent create(String soundName) {
        SoundEvent sound = new SoundEvent(new ResourceLocation(JurassiCraft.MODID, soundName));
        RegistryHandler.registerSound(sound, soundName);
        return sound;
    }
    
    public static SoundEvent[] createArray(String... soundNames) {
    	SoundEvent[] sounds = new SoundEvent[soundNames.length];
    	for(int i = 0; i < soundNames.length; i++) {
    		SoundEvent sound = new SoundEvent(new ResourceLocation(JurassiCraft.MODID, soundNames[i]));
            RegistryHandler.registerSound(sound, soundNames[i]);
            sounds[i] = sound;
    	}
        
        return sounds;
    }

    public static List<SoundEvent> getSounds()
    {
        return sounds;
    }
}