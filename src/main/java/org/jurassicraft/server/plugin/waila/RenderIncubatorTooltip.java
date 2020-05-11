package org.jurassicraft.server.plugin.waila;

import java.awt.Dimension;

import org.jurassicraft.server.util.LangUtils;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import mcp.mobius.waila.config.OverlayConfig;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RenderIncubatorTooltip implements IWailaTooltipRenderer {

	public static final ResourceLocation GUI = new ResourceLocation("jurassicraft:textures/gui/incubator_waila.png");
	
	@Override
    public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
        return new Dimension(60, 18 * params.length);
    }

	@Override
	public void draw(String[] params, IWailaCommonAccessor accessor) {
		
		int offsetY = 0;
		
		for(int param = 0; param < params.length; param++) {
			
			int slot = Integer.valueOf(params[param].split(";")[0]);
			String name = params[param].split(";")[1];
			int meta = Integer.valueOf(params[param].split(";")[2]);
	        NBTTagCompound tagCompound = null;
	        try {
	            tagCompound = JsonToNBT.getTagFromJson(params[param].split(";")[5]);
	        } catch (NBTException e) {}
	        
	        ItemStack stack = ItemStack.EMPTY;
	            stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)), 1, meta);
	        if (stack.isEmpty())
	            return;

	        if (tagCompound != null)
	        	stack.setTagCompound(tagCompound);
	        
	        String slotS = TextFormatting.GOLD + LangUtils.translate("slot.name") + " " + TextFormatting.WHITE + (slot + 1);
	        DisplayUtil.drawString(slotS, 0, offsetY + 6, OverlayConfig.fontcolor, true);
	        
	        RenderHelper.enableGUIStandardItemLighting();
	        DisplayUtil.renderStack(Minecraft.getMinecraft().fontRenderer.getStringWidth(slotS) + 3, offsetY, stack);
	        RenderHelper.disableStandardItemLighting();
	        Minecraft.getMinecraft().getTextureManager().bindTexture(GUI);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        boolean male = Boolean.parseBoolean(params[param].split(";")[4]);
	        Gui.drawModalRectWithCustomSizedTexture(Minecraft.getMinecraft().fontRenderer.getStringWidth(slotS) + 25, offsetY + 7, 0, 0, 14, 5, 14, 15);

	        Gui.drawModalRectWithCustomSizedTexture(Minecraft.getMinecraft().fontRenderer.getStringWidth(slotS) + 25, offsetY + 7, 0, (male ? 10 : 5), Integer.valueOf(params[param].split(";")[3]), 5, 14, 15);
	        
	        offsetY += 17;
		}
		
	}

}
