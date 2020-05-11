package org.jurassicraft.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class ThirdPersonViewRenderer {

	private static ThirdPersonViewRenderer INSTANCE;
	private Minecraft mc;

	private final float maxThirdPersonDistance = 20;
	private final float minThirdPersonDistance = 4.0F;
	private float thirdPersonDistance = 4.0F;

	public ThirdPersonViewRenderer() {
		mc = Minecraft.getMinecraft();
	}

	public void transformThirdPersonViewRender(double renderPartialTicks) {
		if (mc.gameSettings.thirdPersonView > 0) {
			Entity entity = mc.getRenderViewEntity();
			float yaw = entity.rotationYaw;
			float pitch = entity.rotationPitch;
			GlStateManager.rotate(entity.rotationPitch - pitch, 1, 0, 0);
			GlStateManager.rotate(entity.rotationYaw - yaw, 0, 1, 0);
			GlStateManager.translate(0, 0, getDist(entity, 4, renderPartialTicks));
			GlStateManager.translate(0, 0, -getDist(entity, this.thirdPersonDistance, renderPartialTicks));
			GlStateManager.rotate(yaw - entity.rotationYaw, 0, 1, 0);
			GlStateManager.rotate(pitch - entity.rotationPitch, 1, 0, 0);
		}
	}

	private double getDist(Entity entity, double max, double partialTicks) {
		float yaw = entity.rotationYaw * 0.017453292F;
		float pitch = entity.rotationPitch * 0.017453292F;
		double ex = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
		double ey = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + entity.getEyeHeight();
		double ez = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
		if (mc.gameSettings.thirdPersonView == 2) {
			pitch += 180;
		}
		double cx = (-MathHelper.sin(yaw) * MathHelper.cos(pitch)) * max;
		double cy = (MathHelper.cos(yaw) * MathHelper.cos(pitch)) * max;
		double cz = (-MathHelper.sin(pitch)) * max;
		for (int i = 0; i < 8; ++i) {
			float mx = ((i & 1) * 2 - 1) / 10F;
			float my = ((i >> 1 & 1) * 2 - 1) / 10F;
			float mz = ((i >> 2 & 1) * 2 - 1) / 10F;
			RayTraceResult r = mc.world.rayTraceBlocks(new Vec3d(ex + mx, ey + my, ez + mz),
					new Vec3d(ex - cx + mx + mz, ey - cz + my, ez - cy + mz));
			if (r != null) {
				double dist = r.hitVec.distanceTo(new Vec3d(ex, ey, ez));
				if (dist < max) {
					max = dist;
				}
			}
		}
		return max;
	}

	public static ThirdPersonViewRenderer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ThirdPersonViewRenderer();
		}
		return INSTANCE;
	}

	public void setThirdPersonViewDistance(float distance) {
		if (distance < this.minThirdPersonDistance) {
			this.thirdPersonDistance = this.minThirdPersonDistance;
		} else if (distance > this.maxThirdPersonDistance) {
			this.thirdPersonDistance = this.maxThirdPersonDistance;
		} else {
			this.thirdPersonDistance = distance;
		}
	}

	public float getThirdPersonViewDistance() {
		return this.thirdPersonDistance;
	}

	public float getMinThirdPersonViewDistance() {
		return this.minThirdPersonDistance;
	}

	public float getMaxThirdPersonViewDistance() {
		return this.maxThirdPersonDistance;
	}

	public float getDefaultThirdPersonDistance() {
		return this.minThirdPersonDistance;
	}
}