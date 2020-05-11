package org.jurassicraft.client.render.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.OrientedBlock;
import org.jurassicraft.server.block.entity.CleaningStationBlockEntity;

public class CleaningStationRenderer extends TileEntitySpecialRenderer<CleaningStationBlockEntity> {

	@Override
	public void render(final CleaningStationBlockEntity tileEntity, final double x, final double y, final double z, final float particialTicks, final int p_180535_9_, final float alpha) {
		final World world = tileEntity.getWorld();

		final IBlockState state = world.getBlockState(tileEntity.getPos());

		if (state.getBlock() == BlockHandler.CLEANING_STATION) {
			EnumFacing value = state.getValue(OrientedBlock.FACING);

			if (value == EnumFacing.NORTH || value == EnumFacing.SOUTH) {
				value = value.getOpposite();
			}

			final int rotation = value.getHorizontalIndex() * 90;
			final float scale = 0.25F;

			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 1.5F, z + 0.5);

			GlStateManager.rotate(rotation, 0, 1, 0);
			GlStateManager.translate(0.0, -1.03, -0.05);
			GlStateManager.scale(-scale, -scale, scale);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);

			GlStateManager.rotate((float) tileEntity.getRenderCleaningRotation(particialTicks), 1.0F, 0.0F, 0.0F);

			ClientProxy.MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			final RenderItem renderItem = ClientProxy.MC.getRenderItem();

			final ItemStack cleanable = tileEntity.getStackInSlot(0);

			if (cleanable != null) {
				renderItem.renderItem(cleanable, renderItem.getItemModelMesher().getItemModel(cleanable));
			}

			GlStateManager.popMatrix();
		}
	}
}
