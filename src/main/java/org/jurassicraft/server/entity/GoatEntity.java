package org.jurassicraft.server.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.HashMap;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.client.model.animation.PoseHandler;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.api.Animatable;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.ai.SmartBodyHelper;
import org.jurassicraft.server.food.FoodHelper;
import org.jurassicraft.server.food.FoodType;
import org.jurassicraft.server.item.ItemHandler;

public class GoatEntity extends EntityAnimal implements Animatable, IEntityAdditionalSpawnData {
	public static final PoseHandler<GoatEntity> BILLY_POSE_HANDLER = new PoseHandler<>(new ResourceLocation(JurassiCraft.MODID, "goat_billy"), Lists.newArrayList(GrowthStage.ADULT), EntityHandler.GOAT.getMetadata());
    public static final PoseHandler<GoatEntity> KID_POSE_HANDLER = new PoseHandler<>(new ResourceLocation(JurassiCraft.MODID, "goat_kid"), Lists.newArrayList(GrowthStage.ADULT), EntityHandler.GOAT.getMetadata());
    public static final PoseHandler<GoatEntity> NANNY_POSE_HANDLER = new PoseHandler<>(new ResourceLocation(JurassiCraft.MODID, "goat_nanny"), Lists.newArrayList(GrowthStage.ADULT), EntityHandler.GOAT.getMetadata());
    private static final DataParameter<Boolean> WATCHER_IS_RUNNING = EntityDataManager.createKey(GoatEntity.class, DataSerializers.BOOLEAN);

    private Animation animation;
    private int animationTick;
    protected Animal animal;
    private int animationLength;
    private boolean billy;
    private Variant variant = Variant.JURASSIC_PARK;
    private boolean milked;
    private boolean inLava;
    private byte animationVariant = 0;

    public GoatEntity(World world) {
        super(world);
        this.setSize(0.6F, 1.2F);
        this.stepHeight = 1.0F;
        this.animationTick = 0;
        this.setAnimation(EntityAnimation.IDLE.get());
    }

    @Override
    protected EntityBodyHelper createBodyHelper() {
        return new SmartBodyHelper(this);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIEatGrass(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25, false, Sets.newHashSet(FoodHelper.getFoodItems(FoodType.PLANT))));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.tasks.addTask(8, new EntityAIAvoidEntity<>(this, EntityWolf.class, 6.0F, 1.0F, 1.6F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.animal = EntityHandler.getAnimalByClass(this.getClass());
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(7.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
    }

    @Override
    public void addVariant(Animation animation, byte variant) {}
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(WATCHER_IS_RUNNING, false);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable mate) {
        GoatEntity entity = new GoatEntity(this.world);
        entity.onInitialSpawn(this.world.getDifficultyForLocation(this.getPosition()), null);
        return entity;
    }

    @Override
    public boolean isCarcass() {
        return false;
    }
    
    @Override
	public byte getAnimationVariant(Animation animation) {
		return (byte) 0;
	}

    @Override
    public boolean isMoving() {
        float deltaX = (float) (this.posX - this.prevPosX);
        float deltaZ = (float) (this.posZ - this.prevPosZ);
        return deltaX * deltaX + deltaZ * deltaZ > 0.001F;
    }

    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public boolean isSwimming() {
        return (this.isInWater() || this.isInLava()) && !this.onGround;
    }

    @Override
    public boolean isRunning() {
        return this.dataManager.get(WATCHER_IS_RUNNING);
    }

    @Override
    public boolean canUseGrowthStage(GrowthStage growthStage) {
        return growthStage == GrowthStage.ADULT;
    }

    @Override
    public boolean isMarineCreature() {
        return false;
    }

    @Override
    public boolean shouldUseInertia() {
        return true;
    }

    @Override
    public boolean isSleeping() {
        return false;
    }

    @Override
    public boolean inWater() {
        return this.isInWater();
    }

    @Override
    public boolean inLava() {
        return this.inLava;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % 10 == 0) {
            this.inLava = this.isInLava();
        }
        if (this.animation != null && this.animation != EntityAnimation.IDLE.get()) {
            boolean shouldHold = EntityAnimation.getAnimation(this.animation).shouldHold();
            if (this.animationTick < this.animationLength) {
                this.animationTick++;
            } else if (!shouldHold) {
                this.animationTick = 0;
                this.setAnimation(EntityAnimation.IDLE.get());
            } else {
                this.animationTick = this.animationLength - 1;
            }
        }
        if (!this.world.isRemote) {
            this.dataManager.set(WATCHER_IS_RUNNING, this.getAIMoveSpeed() > this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        }
    }

    @Override
    public Animation[] getAnimations() {
        return EntityAnimation.getAnimations();
    }

    @Override
    public Animation getAnimation() {
        return this.animation;
    }

    @Override
    public void setAnimation(Animation newAnimation) {
        Animation oldAnimation = this.animation;
        this.animation = newAnimation;
        if (oldAnimation != newAnimation) {
            this.animationTick = 0;
            this.animationLength = (int) this.getPoseHandler().getAnimationLength(this.animation, this.getGrowthStage(), this.getAnimationVariant(newAnimation));
            EntityHandler.sendSpecialAnimationMessage(this, newAnimation, (byte) 0);
        }
    }

    @Override
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animationTick = tick;
    }

    @Override
    public GrowthStage getGrowthStage() {
        return this.isChild() ? GrowthStage.INFANT : GrowthStage.ADULT;
    }

    @Override
    public PoseHandler getPoseHandler() {
    	return this.isChild() ? KID_POSE_HANDLER : this.billy ? BILLY_POSE_HANDLER : NANNY_POSE_HANDLER;
    }

    public Type getType() {
        return this.isChild() ? Type.KID : this.billy ? Type.BILLY : Type.NANNY;
    }

    @Override
    public void playLivingSound() {
        super.playLivingSound();
        if (this.getAnimation() == EntityAnimation.IDLE.get()) {
            this.setAnimation(EntityAnimation.SPEAK.get());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundHandler.GOAT_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundHandler.GOAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundHandler.GOAT_DEATH;
    }

    @Override
    protected float getJumpUpwardsMotion() {
        return 0.62F;
    }

    @Override
    public void eatGrassBonus() {
        super.eatGrassBonus();
        this.milked = false;
        this.setAnimation(EntityAnimation.EATING.get());
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    	ItemStack stack =  player.getHeldItem(hand);
        if (stack != null && stack.getItem() == Items.BUCKET && !player.capabilities.isCreativeMode && !this.isChild() && !this.milked && !this.billy) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            stack.shrink(1);
            if (stack.getCount() == 0) {
                player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
            } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
                player.dropItem(new ItemStack(Items.MILK_BUCKET), false);
            }
            this.milked = true;
            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
        this.billy = this.rand.nextBoolean();
        this.variant = Variant.values()[this.rand.nextInt(Variant.values().length)];
        return super.onInitialSpawn(difficulty, data);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Billy", this.billy);
        compound.setByte("Variant", (byte) this.variant.ordinal());
        compound.setBoolean("Milked", this.milked);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.billy = compound.getBoolean("Billy");
        this.variant = Variant.values()[compound.getByte("Variant")];
        this.milked = compound.getBoolean("Milked");
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(this.billy);
        buffer.writeByte(this.variant.ordinal());
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.billy = additionalData.readBoolean();
        this.variant = Variant.values()[additionalData.readByte()];
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && FoodHelper.isFoodType(stack.getItem(), FoodType.PLANT);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        this.dropItem(Items.LEATHER, this.rand.nextInt(2) + 1);
        if (this.rand.nextBoolean()) {
            this.entityDropItem(new ItemStack(Blocks.WOOL, 1, this.rand.nextBoolean() ? EnumDyeColor.BROWN.getMetadata() : EnumDyeColor.WHITE.getMetadata()), 0.0F);
        }
        this.dropItem(this.isBurning() ? ItemHandler.GOAT_COOKED : ItemHandler.GOAT_RAW, this.rand.nextInt(2) + 1);
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 0.8F;
    }

    public Variant getVariant() {
        return this.variant;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 3;
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    public boolean canMateWith(EntityAnimal other) {
        if (other instanceof GoatEntity) {
            GoatEntity goat = (GoatEntity) other;
            return goat.getType() == Type.NANNY && this.getType() == Type.BILLY || goat.getType() == Type.BILLY && this.getType() == Type.NANNY;
        }
        return super.canMateWith(other);
    }

    public static enum Type {
        BILLY,
        NANNY,
        KID
    }

    public static enum Variant {
        JURASSIC_WORLD,
        JURASSIC_PARK,
        JPOG
    }

	@Override
	public HashMap<Animation, Byte> getVariants() {
		return new HashMap<>();
	}

	@Override
	public void setAnimationWithVariant(Animation newAnimation, byte variant) {
		this.setAnimation(newAnimation);
	}

	@Override
	public Class getEntityClass() {
		return this.getClass();
	}

}
