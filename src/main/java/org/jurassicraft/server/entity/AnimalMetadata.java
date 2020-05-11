package org.jurassicraft.server.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import org.jurassicraft.server.dinosaur.DinosaurMetadata;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.ai.util.MovementType;
import org.jurassicraft.server.period.TimePeriod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class AnimalMetadata {
	
    protected final ResourceLocation identifier;
    private Class<? extends EntityLivingBase> entityClass;
    private Function<World, EntityLivingBase> entityConstructor;
    protected HashMap<String, Float> offsetCubes = new HashMap<String, Float>();
    private int primaryEggColor, secondaryEggColor;
    
    public AnimalMetadata(final ResourceLocation identifier) {
        this.identifier = identifier;
    }
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }
    public AnimalMetadata setAnimalEntity(final Class<? extends EntityLivingBase> clazz, final Function<World, EntityLivingBase> constructor) {
        this.entityClass = clazz;
        this.entityConstructor = constructor;
        return this;
    }
    public AnimalMetadata setAnimalOffsetCubes(final HashMap<String, Float> cubes) {
        this.offsetCubes = cubes;
        return this;
    }
    public HashMap<String, Float> getOffsetCubes() {
        return this.offsetCubes;
    }
    public int getEggPrimaryColor() {
        return this.primaryEggColor;
    }
    public int getEggSecondaryColor() {
        return this.secondaryEggColor;
    }
    public AnimalMetadata setEggColor(final int primary, final int secondary) {
        this.primaryEggColor = primary;
        this.secondaryEggColor = secondary;
        return this;
    }
    public EntityLivingBase construct(final World world) {
        return (EntityLivingBase) this.entityConstructor.apply(world);
    }
    public Class<? extends EntityLivingBase> getAnimalClass() {
        return (Class<? extends EntityLivingBase>) this.entityClass;
    }
}