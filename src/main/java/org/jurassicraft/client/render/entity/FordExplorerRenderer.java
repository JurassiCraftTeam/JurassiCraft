package org.jurassicraft.client.render.entity;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.model.ResetControlTabulaModel;
import org.jurassicraft.client.model.animation.entity.vehicle.CarAnimator;
import org.jurassicraft.client.model.animation.entity.vehicle.CarAnimator.Door;
import org.jurassicraft.server.entity.vehicle.VehicleEntity;
import org.jurassicraft.server.entity.vehicle.FordExplorerEntity;
import org.jurassicraft.server.tabula.TabulaModelHelper;

import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FordExplorerRenderer extends CarRenderer<FordExplorerEntity> {

    public FordExplorerRenderer(RenderManager manager) {
        super(manager, "ford_explorer");
    }

    @Override
    protected CarAnimator createCarAnimator() {
        return new CarAnimator()
                .addDoor(new Door("door left main", 0, true))
                .addDoor(new Door("door right main", 1, false))
                .addDoor(new Door("Back door left main", 2, true))
                .addDoor(new Door("Back door right main", 3, false));
    }
}