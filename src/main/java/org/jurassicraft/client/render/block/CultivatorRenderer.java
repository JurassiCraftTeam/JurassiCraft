package org.jurassicraft.client.render.block;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.entity.CultivatorBlockEntity;
import org.jurassicraft.server.entity.DinosaurEntity;

import javax.vecmath.Vector3f;

public class CultivatorRenderer extends TileEntitySpecialRenderer<CultivatorBlockEntity> {

    @Override
    public void render(final CultivatorBlockEntity tileEntity, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(x + 0.5, y + 1, z + 0.5);

//        if(tileEntity.isProcessing(0) && tileEntity.getField(1) != 0) {
//            DinosaurEntity entity = tileEntity.getDinosaurEntity();
//            float percentageDone = tileEntity.getField(0) / (float)tileEntity.getField(1);
//            Vector3f vec = entity.getDinosaurCultivatorRotation();
//            GlStateManager.rotate(90F, 1, 0, 0);
//            GlStateManager.rotate(vec.y, 0, 1, 0);
//            GlStateManager.rotate(vec.z, 0, 0, 1);
//            GlStateManager.scale(percentageDone, percentageDone, percentageDone);
//            if (entity != null) {
//                this.mc.getRenderManager().renderEntity(entity, 0, 0, 0, 0, 0, false);
//            }
//        }
//
//        if(tileEntity.isProcessing(0))
        {
        	final long timer = getWorld().getTotalWorldTime();
        	final ItemStack stack = tileEntity.getStackInSlot(0);
            GlStateManager.rotate((timer * 4F) % 360, 0 , 1, 0);
            GlStateManager.translate(0, Math.sin(timer / 7D) * 0.5f, 0);
            ClientProxy.MC.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        }

        GlStateManager.popMatrix();
    }
}
