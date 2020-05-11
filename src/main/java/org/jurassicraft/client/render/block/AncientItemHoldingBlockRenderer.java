package org.jurassicraft.client.render.block;

import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.OrientedBlock;
import org.jurassicraft.server.block.entity.AncientItemHoldingBlockEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class AncientItemHoldingBlockRenderer extends TileEntitySpecialRenderer<AncientItemHoldingBlockEntity> {

	@Override
	public void render(final AncientItemHoldingBlockEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {

		if (te.getDisplayItemStack() != null) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5F + te.getDisplayItemXOffset(), y + 0.5F + te.getDisplayItemYOffset(), z + 0.5F + te.getDisplayItemZOffset());
			GlStateManager.disableLighting();

			final float scale = 0.7f;
			GlStateManager.rotate(te.getItemRotation(), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(90, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.pushAttrib();

			RenderHelper.enableStandardItemLighting();
			ClientProxy.MC.getRenderItem().renderItem(te.getDisplayItemStack(), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.disableStandardItemLighting();

			GlStateManager.popAttrib();
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}
}
