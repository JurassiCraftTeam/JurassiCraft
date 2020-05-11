package org.jurassicraft.server.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public abstract class FlyingDinosaurEntity extends DinosaurEntity {
    public FlyingDinosaurEntity(World world) {
        super(world);
        this.moveHelper = new FlyingDinosaurEntity.FlyingMoveHelper();
        this.tasks.addTask(5, new FlyingDinosaurEntity.AIRandomFly());
        this.tasks.addTask(7, new FlyingDinosaurEntity.AILookAround());
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    //TODO: Make sure friction is moveRelative works okay
    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.inWater()) {
            this.moveRelative(strafe, forward, 0.02F, 0F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
        } else if (this.inLava()) {
            this.moveRelative(strafe, forward, 0.02F, 0F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        } else {
            float friction = 0.91F;

            if (this.onGround) {
                friction = this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ))).getBlock().slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (friction * friction * friction);
            this.moveRelative(strafe, forward, this.onGround ? f3 * 0.1F : 0.02F, 0F);
            friction = 0.91F;

            if (this.onGround) {
                friction = this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ))).getBlock().slipperiness * 0.91F;
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double) friction;
            this.motionY *= (double) friction;
            this.motionZ *= (double) friction;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double moveX = this.posX - this.prevPosX;
        double moveZ = this.posZ - this.prevPosZ;
        float dist = MathHelper.sqrt(moveX * moveX + moveZ * moveZ) * 4.0F;

        if (dist > 1.0F) {
            dist = 1.0F;
        }

        this.limbSwingAmount += (dist - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    class AIRandomFly extends EntityAIBase {
        private FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;

        public AIRandomFly() {
            this.setMutexBits(1);
        }

        @Override
        public boolean shouldExecute() {
            EntityMoveHelper moveHelper = this.dino.getMoveHelper();

            if (!moveHelper.isUpdating()) {
                return true;
            } else {
                double moveX = moveHelper.getX() - this.dino.posX;
                double moveY = moveHelper.getY() - this.dino.posY;
                double moveZ = moveHelper.getZ() - this.dino.posZ;
                double distance = moveX * moveX + moveY * moveY + moveZ * moveZ;
                return distance < 1.0D || distance > 3600.0D;
            }
        }
        
        @Override
        public boolean shouldContinueExecuting() {
            return false;
        }

        @Override
        public void startExecuting() {
            Random random = this.dino.getRNG();
            double destinationX = this.dino.posX + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double destinationY = this.dino.posY + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double destinationZ = this.dino.posZ + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.dino.getMoveHelper().setMoveTo(destinationX, destinationY, destinationZ, 1.0D);
        }
    }

    class FlyingMoveHelper extends EntityMoveHelper {
        private FlyingDinosaurEntity parentEntity = FlyingDinosaurEntity.this;
        private int timer;

        public FlyingMoveHelper() {
            super(FlyingDinosaurEntity.this);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                double distanceX = this.posX - this.parentEntity.posX;
                double distanceY = this.posY - this.parentEntity.posY;
                double distanceZ = this.posZ - this.parentEntity.posZ;
                double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;

                if (this.timer-- <= 0) {
                    this.timer += this.parentEntity.getRNG().nextInt(5) + 2;
                    distance = (double) MathHelper.sqrt(distance);

                    if (this.isNotColliding(this.posX, this.posY, this.posZ, distance)) {
                        this.parentEntity.motionX += distanceX / distance * 0.1D;
                        this.parentEntity.motionY += distanceY / distance * 0.1D;
                        this.parentEntity.motionZ += distanceZ / distance * 0.1D;
                    } else {
                        this.action = EntityMoveHelper.Action.WAIT;
                    }
                }
            }
        }

        private boolean isNotColliding(double x, double y, double z, double distance) {
            double d0 = (x - this.parentEntity.posX) / distance;
            double d1 = (y - this.parentEntity.posY) / distance;
            double d2 = (z - this.parentEntity.posZ) / distance;
            AxisAlignedBB bounds = this.parentEntity.getEntityBoundingBox();

            for (int i = 1; (double) i < distance; ++i) {
                bounds = bounds.offset(d0, d1, d2);

                if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, bounds).isEmpty()) {
                    return false;
                }
            }

            return true;
        }
    }

    class AILookAround extends EntityAIBase {
        private FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;

        public AILookAround() {
            this.setMutexBits(2);
        }

        @Override
        public boolean shouldExecute() {
            return true;
        }

        @Override
        public void updateTask() {
            if (this.dino.getAttackTarget() == null) {
                this.dino.renderYawOffset = this.dino.rotationYaw = -((float) Math.atan2(this.dino.motionX, this.dino.motionZ)) * 180.0F / (float) Math.PI;
            } else {
                EntityLivingBase attackTarget = this.dino.getAttackTarget();
                double maxDistance = 64.0D;

                if (attackTarget.getDistanceSq(this.dino) < maxDistance * maxDistance) {
                    double diffX = attackTarget.posX - this.dino.posX;
                    double diffZ = attackTarget.posZ - this.dino.posZ;
                    this.dino.renderYawOffset = this.dino.rotationYaw = -((float) Math.atan2(diffX, diffZ)) * 180.0F / (float) Math.PI;
                }
            }
        }
    }
}
