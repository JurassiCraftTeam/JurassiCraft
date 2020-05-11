package org.jurassicraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.client.model.animation.entity.vehicle.CarAnimator;
import org.jurassicraft.server.entity.vehicle.JeepWranglerEntity;

@SideOnly(Side.CLIENT)
public class JeepWranglerRenderer extends CarRenderer<JeepWranglerEntity> {

    public JeepWranglerRenderer(RenderManager manager) {
        super(manager, "jeep_wrangler");
    }

    @Override
    protected CarAnimator createCarAnimator() {
        return new CarAnimator()
                .addDoor(new CarAnimator.Door("door left main", 0, true))
                .addDoor(new CarAnimator.Door("door right main", 1, false));
    }
}