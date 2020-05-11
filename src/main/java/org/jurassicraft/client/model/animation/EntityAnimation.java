package org.jurassicraft.client.model.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jurassicraft.server.entity.dinosaur.TyrannosaurusEntity;

import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.ilexiconn.llibrary.server.network.AnimationMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

public enum EntityAnimation {
	
    IDLE(false, false, false),
    ATTACKING(false, false),
    INJURED(false, false),
    HEAD_COCKING,
    CALLING,
    HISSING,
    POUNCING(false, false),
    SNIFFING,
    EATING,
    DRINKING,
    MATING(false, false),
    SLEEPING(true, false),
    RESTING(true, true),
    ROARING(new IdentifierContainer(TyrannosaurusEntity.class, 2)),
    SPEAK(false, false),
    LOOKING_LEFT,
    LOOKING_RIGHT,
    BEGGING,
    SNAP,
    DYING(true, false, false),
    SCRATCHING,
    SPITTING,
    PECKING,
    PREENING,
    TAIL_DISPLAY,
    REARING_UP,
    LAYING_EGG,
    GIVING_BIRTH,
    GLIDING(true, false, true),
    ON_LAND(false, true, false),
    WALKING(false, false, false), RUNNING(false, false, false), SWIMMING(false, false, false), FLYING(false, false, false), CLIMBING(false, false, false),
    PREPARE_LEAP(false, false), LEAP(true, false), LEAP_LAND(true, false, false),
    START_CLIMBING(false, false),
    DILOPHOSAURUS_SPIT(false, false);

    private Animation animation;
    private boolean hold;
    private boolean doesBlockMovement;
    private boolean useInertia;
    private Map<Class, Integer> variants;

    EntityAnimation(boolean hold, boolean blockMovement, IdentifierContainer... variants) {
        this(hold, blockMovement, true, variants);
    }
    
    EntityAnimation(boolean hold, boolean blockMovement) {
        this(hold, blockMovement, true, new IdentifierContainer[0]);
    }
    
    EntityAnimation(boolean hold, boolean blockMovement, boolean useInertia) {
    	this(hold, blockMovement, useInertia, new IdentifierContainer[0]);
    }

    EntityAnimation(boolean hold, boolean blockMovement, boolean useInertia, IdentifierContainer... variants) {
        this.hold = hold;
        this.doesBlockMovement = blockMovement;
        this.useInertia = useInertia;
        this.variants = Arrays.stream(variants).collect(Collectors.toMap(IdentifierContainer::getName, IdentifierContainer::getVariant));
    
    }

    EntityAnimation(IdentifierContainer... variants) {
        this(false, true, variants);
    }
    
    EntityAnimation() {
        this(false, true, new IdentifierContainer[0]);
    }

    public static Animation[] getAnimations() {
        Animation[] animations = new Animation[values().length];

        for (int i = 0; i < animations.length; i++) {
            animations[i] = values()[i].get();
        }

        return animations;
    }

    public static EntityAnimation getAnimation(Animation animation) {
        for (EntityAnimation animations : values()) {
            if (animation.equals(animations.animation)) {
                return animations;
            }
        }

        return EntityAnimation.IDLE;
    }

    public Animation get() {
        if (this.animation == null) {
            this.animation = Animation.create(-1);
        }

        return this.animation;
    }

    public boolean shouldHold() {
        return this.hold;
    }
    
    public boolean hasVariants(Class name) {
    	return this.variants.containsKey(name) ? this.variants.get(name) > 0 : false;
    }
    
    public int getVariants(Class name) {
    	return this.variants.getOrDefault(name, 0);
    }

    public boolean doesBlockMovement() {
        return this.doesBlockMovement;
    }

    public boolean useInertia() {
        return this.useInertia;
    }
    
    private static class IdentifierContainer {
    	
    	private final Class name;
    	private final int variants;
    	
    	public IdentifierContainer(Class name, int variants) {
    		this.name = name;
    		this.variants = variants;
    	}
    	
    	public Class getName(){
    		return this.name;
    	}
    	
    	public int getVariant(){
    		return this.variants;
    	}
    	
    }
    
}
