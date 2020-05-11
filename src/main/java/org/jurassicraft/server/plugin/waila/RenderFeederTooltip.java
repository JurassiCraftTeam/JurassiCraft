package org.jurassicraft.server.plugin.waila;

import java.awt.Dimension;
import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderFeederTooltip implements IWailaTooltipRenderer {

	public static final ResourceLocation ICONS = new ResourceLocation("jurassicraft:textures/gui/icons.png");
	
	@Override
    public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
        return new Dimension(93, 22);
    }

	@Override
	public void draw(String[] params, IWailaCommonAccessor accessor) {
		float carn = Float.valueOf(params[0]);
		float herb = Float.valueOf(params[1]);

		Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
		int offsetX = 0;
		int offsetY = 0;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		for (int index = 1; index <= 10; index++) {

			if (index <= Math.floor(carn)) {
				Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 20, 0, 10, 10, 30, 20);
				offsetX += 10;
			}

			else if ((index > carn) && (index < carn + 0.5)) {
				Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 10, 0, 10, 10, 30, 20);
				offsetX += 10;
			}

			else if (index >= carn + 0.5) {
				Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, 10, 10, 30, 20);
				offsetX += 10;
			}

		}
		offsetY += 10;
		offsetX = 0;

		for (int index = 1; index <= 10; index++) {

			if (index <= Math.floor(herb)) {
				Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 20, 10, 10, 10, 30, 20);
				offsetX += 10;
			}

			else if ((index > herb) && (index < herb + 0.5)) {
				Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 10, 10, 10, 10, 30, 20);
				offsetX += 10;
			}

			else if (index >= herb + 0.5) {
				Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 10, 10, 10, 30, 20);
				offsetX += 10;
			}

		}
	}

}
