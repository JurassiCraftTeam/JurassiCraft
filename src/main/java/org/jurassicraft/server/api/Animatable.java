package org.jurassicraft.server.api;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

import org.jurassicraft.client.model.animation.PoseHandler;
import org.jurassicraft.server.entity.GrowthStage;

public interface Animatable extends IAnimatedEntity {
    boolean isCarcass();
    boolean isMoving();
    boolean isClimbing();
    boolean inWater();
    boolean isSwimming();
    boolean isRunning();
    boolean inLava();
    boolean canUseGrowthStage(GrowthStage growthStage);
    boolean isMarineCreature();
    boolean shouldUseInertia();
    boolean isSleeping();
    void setAnimationWithVariant(Animation animation, byte variant);
    void addVariant(Animation animation, byte variant);
    HashMap<Animation, Byte> getVariants();
    GrowthStage getGrowthStage();
    Class getEntityClass();
    byte getAnimationVariant(Animation animation);
    <ENTITY extends EntityLivingBase & Animatable> PoseHandler<ENTITY> getPoseHandler();
}
