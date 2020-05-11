package org.jurassicraft.client.render.overlay;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.vehicle.HelicopterEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLSync;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class HelicopterHUDRenderer {

	public static int marginLeft = 25;
	private static float hudScale = 1;

	public HelicopterHUDRenderer() {

	}

	public static void render(HelicopterEntity heli, float partialTicks) {
		HudOverlay overlay = heli.getHudOverlay();
		overlay.render(partialTicks);
	}

	public static class HudOverlay {
		private HashMap<Class, HudElement> hudElements;

		public HudOverlay() {
			this.hudElements = new HashMap<Class, HudElement>();
		}

		public void addHudElement(HudElement element) {
			this.hudElements.put(element.getClass(), element);
		}

		public void removeHudElement(Class cls) {
			this.hudElements.remove(cls);
		}

		public HudElement getHudElement(Class cls) {
			return this.hudElements.get(cls);
		}

		public void enableHudElement(Class<HudElement> cls) {
			try {
				this.addHudElement(cls.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		public boolean isHudElementEnabled(Class<HudElement> cls) {
			return this.hudElements.containsKey(cls);
		}

		public void updateHudElement(Class cls, Object... param) {
			if (this.isHudElementEnabled(cls)) {
				this.hudElements.get(cls).update(param);
			}
		}

		public HashMap<Class, HudElement> getHudElements() {
			return this.hudElements;
		}

		public void render(float partialTicks) {
			hudScale = (Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) ? 0.5f : 1f;
			int i = 0;
			for (Map.Entry<Class, HudElement> entry : this.hudElements.entrySet()) {
				entry.getValue().render(partialTicks);
				i++;
			}
		}

	}

	public static abstract class HudElement extends Gui {

		protected ResourceLocation backgroundTexture;
		protected ResourceLocation needleTexture;
		protected int needleRotation = 0;
		protected ResourceLocation pointerTexture;
		protected int pointerRotation = 0;
		protected float value = 0;
		protected boolean shouldRenderText = true;
		protected static Minecraft mc = Minecraft.getMinecraft();
		protected int index = 0;

		public HudElement() {

		}

		public abstract void update(Object... param);

		public void render(float partialTicks) {
			ScaledResolution scaledResolution = new ScaledResolution(mc);

			int xPosition = scaledResolution.getScaledWidth();
			int yPosition = scaledResolution.getScaledHeight();

			// Background
			GlStateManager.pushMatrix();
			GlStateManager.enableAlpha();
			mc.renderEngine.bindTexture(this.backgroundTexture);
			drawModalRectWithCustomSizedTexture((int) (marginLeft + (this.index * 75) * hudScale), (int) (yPosition - 36 - 64 * hudScale), 0, 0, (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale));
			GlStateManager.disableAlpha();
			GlStateManager.popMatrix();

			// Text
			if (this.shouldRenderText) {
				GlStateManager.pushMatrix();
				GlStateManager.disableDepth();
				float textScale = 0.7f * hudScale;
				GL11.glScalef(textScale, textScale, 1);
				String valueAsText = String.valueOf((int) (this.value));
				for (int i = 1; i <= 5; i++) {
					float offsetX = ((float) i - 1.0f) * -4.7f;
					mc.fontRenderer.drawString("" + ((valueAsText.length() - i < 0) ? "0" : valueAsText.charAt(valueAsText.length() - i)), (int) ((marginLeft + (this.index * 75 + 39.5f + offsetX) * hudScale) / textScale),
							(int) ((yPosition - 36 - (64 - 21.5f) * hudScale) / textScale), Color.WHITE.getRGB());
				}
				GL11.glScalef(1f, 1f, 1);
				GlStateManager.enableDepth();
				GlStateManager.popMatrix();
			}

			// Needle
			if (this.needleTexture != null) {
				GlStateManager.pushMatrix();
				GlStateManager.enableAlpha();
				mc.renderEngine.bindTexture(this.needleTexture);
				GlStateManager.translate((marginLeft + (this.index * 75) * hudScale + (64 * hudScale) / 2), (yPosition - 36 - 64 * hudScale + (64 * hudScale) / 2), 0);
				GlStateManager.rotate(this.needleRotation, 0, 0, 1);
				GlStateManager.translate(-(marginLeft + (this.index * 75) * hudScale + (64 * hudScale) / 2), -(yPosition - 36 - 64 * hudScale + (64 * hudScale) / 2), 0);
				drawModalRectWithCustomSizedTexture((int) (marginLeft + (this.index * 75) * hudScale), (int) (yPosition - 36 - 64 * hudScale), 0, 0, (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale),
						(int) (64 * hudScale));
				GlStateManager.disableAlpha();
				GlStateManager.popMatrix();
			}

			// Pointer
			if (this.pointerTexture != null) {
				GlStateManager.pushMatrix();
				GlStateManager.enableAlpha();
				mc.renderEngine.bindTexture(this.pointerTexture);
				GlStateManager.translate((marginLeft + (this.index * 75) * hudScale + (64 * hudScale) / 2), (yPosition - 36 - 64 * hudScale + (64 * hudScale) / 2), 0);
				GlStateManager.rotate(this.pointerRotation, 0, 0, 1);
				GlStateManager.translate(-(marginLeft + (this.index * 75) * hudScale + (64 * hudScale) / 2), -(yPosition - 36 - 64 * hudScale + (64 * hudScale) / 2), 0);
				drawModalRectWithCustomSizedTexture((int) (marginLeft + (this.index * 75) * hudScale), (int) (yPosition - 36 - 64 * hudScale), 0, 0, (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale),
						(int) (64 * hudScale));
				GlStateManager.disableAlpha();
				GlStateManager.popMatrix();
			}
		}
	}

	public static class HudElementAltimeter extends HudElement {

		public HudElementAltimeter() {
			this.backgroundTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/altimeter.png");
			this.needleTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/altimeter_needle.png");
			this.pointerTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/altimeter_pointer.png");
			this.index = 2;
		}

		@Override
		public void update(Object... param) {
			this.value = (float) param[0];
			this.needleRotation = (int) (((float) param[0] - 63f) / (250f - 63f) * 360f);
			this.pointerRotation = (int) ((float) param[1] / 250f * 360f);
		}
	}

	public static class HudElementArtificialHorizon extends HudElement {
		private float pitchLevel = 0f;
		private float prevPitchLevel = 0f;
		private ResourceLocation secondLayer;
		private ResourceLocation alpha;

		public HudElementArtificialHorizon() {
			this.backgroundTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/artificial_horizon.png");
			this.needleTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/artificial_horizon_needle.png");
			this.pointerTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/artificial_horizon_pointer.png");
			this.secondLayer = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/artificial_horizon_second_layer.png");
			this.alpha = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/artificial_horizon_alpha.png");
			this.index = 1;
		}

		@Override
		public void update(Object... param) {
			this.needleRotation = (int) ((float) param[0]);
			this.prevPitchLevel = this.pitchLevel;
			this.pitchLevel = (float) param[1];
		}

		@Override
		public void render(float partialTicks) {
			ScaledResolution scaledResolution = new ScaledResolution(mc);

			int xPosition = scaledResolution.getScaledWidth();
			int yPosition = scaledResolution.getScaledHeight();
			GlStateManager.enableAlpha();
			GlStateManager.pushMatrix();
			this.renderHorizon(xPosition, yPosition, scaledResolution.getScaleFactor(), partialTicks);
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			mc.renderEngine.bindTexture(this.secondLayer);
			drawModalRectWithCustomSizedTexture((int) (marginLeft + (this.index * 75) * hudScale), (int) (yPosition - 36 - 64 * hudScale), 0, 0, (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale));
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			mc.renderEngine.bindTexture(this.needleTexture);
			GlStateManager.translate((marginLeft + (this.index * 75) * hudScale + (64 * hudScale) / 2), (yPosition - 36 - 64 * hudScale + (64 * hudScale) / 2), 0);
			GlStateManager.rotate(this.needleRotation, 0, 0, 1);
			GlStateManager.translate(-(marginLeft + (this.index * 75) * hudScale + (64 * hudScale) / 2), -(yPosition - 36 - 64 * hudScale + (64 * hudScale) / 2), 0);
			drawModalRectWithCustomSizedTexture((int) (marginLeft + (this.index * 75) * hudScale), (int) (yPosition - 36 - 64 * hudScale), 0, 0, (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale));
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			mc.renderEngine.bindTexture(this.pointerTexture);
			drawModalRectWithCustomSizedTexture((int) (marginLeft + (this.index * 75) * hudScale), (int) (yPosition - 36 - 64 * hudScale), 0, 0, (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale), (int) (64 * hudScale));
			GlStateManager.popMatrix();

			GlStateManager.disableAlpha();
			GlStateManager.disableDepth();

			GlStateManager.enableDepth();
		}

		private void renderHorizon(int xPosition, int yPosition, int scaleFactor, float partialTick) {
			float partialPitch = (this.pitchLevel - this.prevPitchLevel) * partialTick;
			float currentDisplayPitch = this.prevPitchLevel + partialPitch;
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			GL11.glScissor((int) ((marginLeft + (this.index * 75) * hudScale) * scaleFactor), 36 * scaleFactor, (int) (64 * hudScale * scaleFactor), (int) (64 * hudScale * scaleFactor));
			// this.enableStencilBuffer(xPosition, yPosition, scaleFactor, partialPitch);

			// Normal rotation
			if (currentDisplayPitch < 45 && currentDisplayPitch > -135) {
				float bottom = ((45f - (currentDisplayPitch)) / 90f) * (58 * hudScale);
				if (bottom > (58 * hudScale)) {
					bottom = 58 * hudScale;
				}
				drawRect((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (yPosition - 36 - (64 - 3) * hudScale), (int) (marginLeft + (this.index * 75 + 64 - 3) * hudScale), (int) (yPosition - 36 - (64 - 3) * hudScale + bottom),
						new Color(73, 179, 255).getRGB());
			}
			if (currentDisplayPitch > -45 && currentDisplayPitch < 135) {
				float top = (int) (((45f + currentDisplayPitch) / 90f) * (58 * hudScale));
				if (top > 57 * hudScale) {
					top = 58 * hudScale;
				}
				drawRect((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (yPosition - 36 - 3 * hudScale - top), (int) (marginLeft + (this.index * 75 + 64 - 3) * hudScale), (int) (yPosition - 36 - 3 * hudScale),
						new Color(107, 42, 0).getRGB());
			}

			if (currentDisplayPitch < 45 && currentDisplayPitch > -45) {
				float y = ((45f - (currentDisplayPitch)) / 90f) * 58 * hudScale;
				drawHorizontalLine((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (marginLeft + (this.index * 75 + 60 - 3) * hudScale), (int) (yPosition - 36 - (64 - 3) * hudScale + y), Color.WHITE.getRGB());
			}

			// Upside down rotation
			if (currentDisplayPitch > 135 || currentDisplayPitch < -135) {
				float bottom = (1 - ((((currentDisplayPitch > 0) ? 180 : -180) - currentDisplayPitch) + 45) / 90f) * (58 * hudScale);
				if (bottom > (58 * hudScale)) {
					bottom = 58 * hudScale;
				}
				drawRect((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (yPosition - 36 - 3 * hudScale - bottom), (int) (marginLeft + (this.index * 75 + 64 - 3) * hudScale), (int) (yPosition - 36 - 3 * hudScale),
						new Color(73, 179, 255).getRGB());
			}
			if (currentDisplayPitch > 135 || currentDisplayPitch < -135) {
				float top = (((((currentDisplayPitch > 0) ? 180 : -180) - currentDisplayPitch) + 45) / 90f) * (58 * hudScale);
				if (top > 58 * hudScale) {
					top = 58 * hudScale;
				}
				drawRect((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (yPosition - 36 - (64 - 3) * hudScale), (int) (marginLeft + (this.index * 75 + 64 - 3) * hudScale), (int) (yPosition - 36 - (64 - 3) * hudScale + top),
						new Color(107, 42, 0).getRGB());

			}

			if (currentDisplayPitch > 135 || currentDisplayPitch < -135) {
				float y = (((((currentDisplayPitch > 0) ? 180 : -180) - currentDisplayPitch) + 45) / 90f) * (58 * hudScale);
				drawHorizontalLine((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (marginLeft + (this.index * 75 + 60 - 3) * hudScale), (int) (yPosition - 36 - (64 - 3) * hudScale + y), Color.WHITE.getRGB());
			}

			GlStateManager.color(255, 255, 255);

			// GL11.glDisable(GL11.GL_STENCIL_TEST);
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
		}

		private void enableStencilBuffer(int xPosition, int yPosition, int scaleFactor, float partialPitch) {
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glEnable(GL11.GL_STENCIL_TEST);
			GL11.glColorMask(false, false, false, false);
			GL11.glDepthMask(false);
			GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xFF);
			GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP); // draw 1s on test fail (always)

			// draw stencil pattern
			GL11.glStencilMask(0xFF);
			GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT); // needs mask=0xFF
			this.drawCircle(xPosition, yPosition, scaleFactor, partialPitch);

			GL11.glColorMask(true, true, true, true);
			GL11.glDepthMask(true);
			GL11.glStencilMask(0x00);
			// draw where stencil's value is 0
			GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
			/* (nothing to draw) */
			// draw only where stencil's value is 1
			GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
		}

		private void drawCircle(int xPosition, int yPosition, int scaleFactor, float partialPitch) {
			if (this.prevPitchLevel + partialPitch > -45) {
				int top = (int) (((45f + this.prevPitchLevel + partialPitch) / 90f) * (58 * hudScale));
				if (top > 58 * hudScale) {
					top = (int) (58 * hudScale);
				}
				drawRect((int) (marginLeft + (this.index * 75 + 3) * hudScale), (int) (yPosition - 36 - 3 * hudScale - top), (int) (marginLeft + (this.index * 75 + 64 - 3) * hudScale), (int) (yPosition - 36 - 3 * hudScale),
						Color.WHITE.getRGB());
			}
		}
	}

	public static class HudElementTachometer extends HudElement {

		public HudElementTachometer() {
			this.backgroundTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/tachometer.png");
			this.needleTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/tachometer_needle.png");
			this.pointerTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/tachometer_pointer.png");
			this.index = 0;
		}

		@Override
		public void update(Object... param) {
			this.value = (float) param[0];
			this.needleRotation = (int) (33 + (float) param[1] * 294);
			this.pointerRotation = (int) (33 + (float) param[2] * 294);
		}
	}

	public static class HudElementCompass extends HudElement {

		public HudElementCompass() {
			this.backgroundTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/compass.png");
			this.needleTexture = new ResourceLocation(JurassiCraft.MODID, "textures/entities/helicopter/hud/compass_needle.png");
			this.index = 3;
			this.shouldRenderText = false;
		}

		@Override
		public void update(Object... param) {
			this.value = (float) param[0];
			this.needleRotation = (int) ((float) param[0]) - 180;
			this.pointerRotation = 0;
		}
	}

	public static class HudElementStatsDisplay extends HudElement {
		boolean autopilotEnabled = false;
		boolean lockOn = false;

		public HudElementStatsDisplay() {

		}

		@Override
		public void update(Object... param) {
			this.autopilotEnabled = (boolean) param[0];
			this.lockOn = (boolean) param[1];
		}

		@Override
		public void render(float partialTicks) {
			ScaledResolution scaledResolution = new ScaledResolution(mc);

			int yPosition = scaledResolution.getScaledHeight();
			GlStateManager.disableDepth();
			if (!this.autopilotEnabled) {
				mc.fontRenderer.drawStringWithShadow("Autopilot disabled!", marginLeft, yPosition - 140, Color.WHITE.getRGB());
			}
			mc.fontRenderer.drawStringWithShadow(this.lockOn ? "Rotation lock on" : "Rotation lock off", marginLeft, yPosition - 130, Color.WHITE.getRGB());
			GlStateManager.enableDepth();
		}

	}
}
