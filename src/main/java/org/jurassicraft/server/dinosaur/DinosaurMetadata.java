package org.jurassicraft.server.dinosaur;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import org.jurassicraft.server.entity.AnimalMetadata;
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

public class DinosaurMetadata extends AnimalMetadata {
	
	private Dinosaur.DinosaurType dinosaurType;
    private int primaryEggColorMale, primaryEggColorFemale;
    private int secondaryEggColorMale, secondaryEggColorFemale;
    private Class<? extends DinosaurEntity> entityClass;
    private Function<World, DinosaurEntity> entityConstructor;
    private TimePeriod timePeriod;
    private double babyHealth, adultHealth;
    private double babyStrength, adultStrength;
    private double babySpeed, adultSpeed;
    private float babySizeX, adultSizeX;
    private float babySizeY, adultSizeY;
    private float babyEyeHeight, adultEyeHeight;
    private double attackSpeed = 1.0;
    private boolean isMarineAnimal;
    private boolean isMammal;
    private int storage;
    private Diet diet;
    private SleepTime sleepTime = SleepTime.DIURNAL;
    private String[] bones;
    private int maximumAge;
    private String[] skeletonPoses = new String[] {"idle"};
    private String headCubeName;
    private MovementType movementType = MovementType.NEAR_SURFACE;
    private Dinosaur.BirthType birthType = Dinosaur.BirthType.EGG_LAYING;
    private boolean isImprintable;
    private boolean randomFlock = true;
    private float scaleInfant;
    private float scaleAdult;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private boolean defendOwner;
    private boolean flee;
    private double flockSpeed = 0.8;
    private double attackBias = 200.0;
    private int maxHerdSize = 32;
    private int spawnChance;
    private Biome[] spawnBiomes;
    private boolean canClimb;
    private int breedCooldown;
    private boolean breedAroundOffspring;
    private int minClutch = 2;
    private int maxClutch = 6;
    private boolean defendOffspring;
    private boolean directBirth;
    private int jumpHeight;
    private String[][] recipe;
    private OverlayType[] overlayTypes = new OverlayType[]{};
    private int overlayCount;
    private int eyeTime = 4;
    
    public DinosaurMetadata(final ResourceLocation identifier) {
    	super(identifier);
    }
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }
    public DinosaurMetadata setEntity(final Class<? extends DinosaurEntity> clazz, final Function<World, DinosaurEntity> constructor) {
        this.entityClass = clazz;
        this.entityConstructor = constructor;
        return this;
    }
    public DinosaurMetadata setEggColorMale(final int primary, final int secondary) {
        this.primaryEggColorMale = primary;
        this.secondaryEggColorMale = secondary;
        return this;
    }
    public DinosaurMetadata setEggColorFemale(final int primary, final int secondary) {
        this.primaryEggColorFemale = primary;
        this.secondaryEggColorFemale = secondary;
        return this;
    }
    public DinosaurMetadata setTimePeriod(final TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }
    public DinosaurMetadata setHealth(final double baby, final double adult) {
        this.babyHealth = baby;
        this.adultHealth = adult;
        return this;
    }
    public DinosaurMetadata setStrength(final double baby, final double adult) {
        this.babyStrength = baby;
        this.adultStrength = adult;
        return this;
    }
    public DinosaurMetadata setSpeed(final double baby, final double adult) {
        this.babySpeed = baby;
        this.adultSpeed = adult;
        return this;
    }
    public DinosaurMetadata setSizeX(final float baby, final float adult) {
        this.babySizeX = baby;
        this.adultSizeX = adult;
        return this;
    }
    public DinosaurMetadata setSizeY(final float baby, final float adult) {
        this.babySizeY = baby;
        this.adultSizeY = adult;
        return this;
    }
    public DinosaurMetadata setEyeHeight(final float baby, final float adult) {
        this.babyEyeHeight = baby;
        this.adultEyeHeight = adult;
        return this;
    }
    public DinosaurMetadata setFlockSpeed(final float speed) {
        this.flockSpeed = speed;
        return this;
    }
    public DinosaurMetadata setAttackBias(final double bias) {
        this.attackBias = bias;
        return this;
    }
    public DinosaurMetadata setMaxHerdSize(final int herdSize) {
        this.maxHerdSize = herdSize;
        return this;
    }
    public DinosaurMetadata setRandomFlock(final boolean randomFlock) {
        this.randomFlock = randomFlock;
        return this;
    }
    public DinosaurMetadata setMovementType(final MovementType type) {
        this.movementType = type;
        return this;
    }
    public DinosaurMetadata setBreeding(final boolean directBirth, final int minClutch, final int maxClutch, final int breedCooldown, final boolean breedAroundOffspring, final boolean defendOffspring) {
        this.directBirth = directBirth;
        this.minClutch = minClutch;
        this.maxClutch = maxClutch;
        this.breedCooldown = breedCooldown * 20 * 60;
        this.breedAroundOffspring = breedAroundOffspring;
        this.defendOffspring = defendOffspring;
        return this;
    }
    public DinosaurMetadata setMaximumAge(final int age) {
        this.maximumAge = age;
        return this;
    }
    public DinosaurMetadata setSkeletonPoses(final String... poses) {
        this.skeletonPoses = poses;
        return this;
    }
    public DinosaurMetadata setOffsetCubes(final HashMap<String, Float> cubes) {
        this.offsetCubes = cubes;
        return this;
    }
    public DinosaurMetadata setOverlayCount(final int count) {
        this.overlayCount = count;
        return this;
    }
    public DinosaurMetadata setOverlays(final OverlayType... types) {
    	this.overlayTypes = types;
    	return this;
    }
    public DinosaurMetadata setAttackSpeed(final double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }
    public DinosaurMetadata setScale(final float scaleAdult, final float scaleInfant) {
        this.scaleInfant = scaleInfant;
        this.scaleAdult = scaleAdult;
        return this;
    }
    public DinosaurMetadata setOffset(final float x, final float y, final float z) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        return this;
    }
    public DinosaurMetadata setDefendOwner(final boolean defendOwner) {
        this.defendOwner = defendOwner;
        return this;
    }
    public DinosaurMetadata setFlee(final boolean flee) {
        this.flee = flee;
        return this;
    }
    public DinosaurMetadata setBones(final String... bones) {
        this.bones = bones;
        return this;
    }
    public DinosaurMetadata setBirthType(final Dinosaur.BirthType birthType) {
        this.birthType = birthType;
        return this;
    }
    public DinosaurMetadata setMarineAnimal(final boolean marineAnimal) {
        this.isMarineAnimal = marineAnimal;
        return this;
    }
    public DinosaurMetadata setEyeTime(final int eyeTime) {
        this.eyeTime = eyeTime;
        return this;
    }
    public DinosaurMetadata setMammal(final boolean isMammal) {
        this.isMammal = isMammal;
        return this;
    }
    public DinosaurMetadata setStorage(final int storage) {
        this.storage = storage;
        return this;
    }
    public DinosaurMetadata setDiet(final Diet diet) {
        this.diet = diet;
        return this;
    }
    public DinosaurMetadata setSleepTime(final SleepTime sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }
    public DinosaurMetadata setHeadCubeName(final String headCubeName) {
        this.headCubeName = headCubeName;
        return this;
    }
    public DinosaurMetadata setImprintable(final boolean imprintable) {
        this.isImprintable = imprintable;
        return this;
    }
    public DinosaurMetadata setCanClimb(final boolean canClimb) {
        this.canClimb = canClimb;
        return this;
    }
    public DinosaurMetadata setJumpHeight(final int jumpHeight) {
        this.jumpHeight = jumpHeight;
        return this;
    }
    public DinosaurMetadata setRecipe(final String[][] recipe) {
        this.recipe = recipe;
        return this;
    }
    public DinosaurMetadata setSpawn(final int chance, final Biome[]... allBiomes) {
        this.spawnChance = chance;
        List<Biome> spawnBiomes = new LinkedList<>();
        for (Biome[] biomes : allBiomes) {
            for (Biome biome : biomes) {
                if (!spawnBiomes.contains(biome)) {
                    spawnBiomes.add(biome);
                }
            }
        }
        this.spawnBiomes = spawnBiomes.toArray(new Biome[0]);
        return this;
    }
    public DinosaurMetadata setSpawn(final int chance, final BiomeDictionary.Type... types) {
        ArrayList<Biome> biomeList = new ArrayList<>();
        for (BiomeDictionary.Type type : types) {
            biomeList.addAll(BiomeDictionary.getBiomes(type));
        }
        this.setSpawn(chance, biomeList.toArray(new Biome[0]));
        return this;
    }
    public DinosaurMetadata setDinosaurType(final Dinosaur.DinosaurType dinosaurType) {
        this.dinosaurType = dinosaurType;
        return this;
    }
    public MovementType getMovementType() {
        return this.movementType;
    }
    public int getEggPrimaryColorMale() {
        return this.primaryEggColorMale;
    }
    public int getEggSecondaryColorMale() {
        return this.secondaryEggColorMale;
    }
    public int getEggPrimaryColorFemale() {
        return this.primaryEggColorFemale;
    }
    public int getEggSecondaryColorFemale() {
        return this.secondaryEggColorFemale;
    }
    public TimePeriod getPeriod() {
        return this.timePeriod;
    }
    public double getBabyHealth() {
        return this.babyHealth;
    }
    public double getAdultHealth() {
        return this.adultHealth;
    }
    public double getBabySpeed() {
        return this.babySpeed;
    }
    public double getAdultSpeed() {
        return this.adultSpeed;
    }
    public double getBabyStrength() {
        return this.babyStrength;
    }
    public double getAdultStrength() {
        return this.adultStrength;
    }
    public float getBabySizeX() {
        return this.babySizeX;
    }
    public Class<? extends DinosaurEntity> getDinosaurClass() {
        return (Class<? extends DinosaurEntity>) this.entityClass;
    }
    public float getBabySizeY() {
        return this.babySizeY;
    }
    public float getAdultSizeX() {
        return this.adultSizeX;
    }
    public float getAdultSizeY() {
        return this.adultSizeY;
    }
    public float getBabyEyeHeight() {
        return this.babyEyeHeight;
    }
    public float getAdultEyeHeight() {
        return this.adultEyeHeight;
    }
    public boolean shouldRandomlyFlock() {
        return this.randomFlock;
    }
    public int getMaximumAge() {
        return this.maximumAge;
    }
    public String[] skeletonPoses() {
        return this.skeletonPoses;
    }
    public double getAttackSpeed() {
        return this.attackSpeed;
    }
    public Dinosaur.BirthType getBirthType() {
        return this.birthType;
    }
    public boolean isMarineCreature() {
        return this.isMarineAnimal;
    }
    public int getEyeTime() {
        return this.eyeTime;
    }
    public boolean isMammal() {
        return this.isMammal;
    }
    public int getLipids() {
        return 1500;
    }
    public int getMinerals() {
        return 1500;
    }
    public int getVitamins() {
        return 1500;
    }
    public int getProximates() // TODO
    {
        return 1500;
    }
    public int getStorage() {
        return this.storage;
    }
    public Diet getDiet() {
        return this.diet;
    }
    public SleepTime getSleepTime() {
        return this.sleepTime;
    }
    public String[] getBones() {
        return this.bones;
    }
    public int getOverlayCount() {
        return this.overlayCount;
    }
    public OverlayType[] getOverlays() {
    	return this.overlayTypes;
    }
    public String getHeadCubeName() {
        return this.headCubeName;
    }
    public double getScaleInfant() {
        return this.scaleInfant;
    }
    public double getScaleAdult() {
        return this.scaleAdult;
    }
    public float getOffsetX() {
        return this.offsetX;
    }
    public float getOffsetY() {
        return this.offsetY;
    }
    public float getOffsetZ() {
        return this.offsetZ;
    }
    public boolean isImprintable() {
        return this.isImprintable;
    }
    public boolean shouldDefendOwner() {
        return this.defendOwner;
    }
    public boolean shouldFlee() {
        return this.flee;
    }
    public double getFlockSpeed() {
        return this.flockSpeed;
    }
    public double getAttackBias() {
        return this.attackBias;
    }
    public int getMaxHerdSize() {
        return this.maxHerdSize;
    }
    public int getSpawnChance() {
        return this.spawnChance;
    }
    public int getJumpHeight() {
        return this.jumpHeight;
    }
    public Biome[] getSpawnBiomes() {
        return this.spawnBiomes;
    }
    public Dinosaur.DinosaurType getDinosaurType() {
        return this.dinosaurType;
    }
    public boolean canClimb() {
        return this.canClimb;
    }
    public int getMinClutch() {
        return this.minClutch;
    }
    public int getMaxClutch() {
        return this.maxClutch;
    }
    public int getBreedCooldown() {
        return this.breedCooldown;
    }
    public boolean shouldBreedAroundOffspring() {
        return this.breedAroundOffspring;
    }
    public boolean shouldDefendOffspring() {
        return this.defendOffspring;
    }
    public boolean givesDirectBirth() {
        return this.directBirth || this.isMammal;
    }
    public String[][] getRecipe() {
        return this.recipe;
    }
    public DinosaurEntity construct(final World world) {
        return (DinosaurEntity) this.entityConstructor.apply(world);
    }
}