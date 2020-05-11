package org.jurassicraft.client.model.animation;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;

public class PosedCuboid {
    public final float rotationX;
    public final float rotationY;
    public final float rotationZ;

    public final float positionX;
    public final float positionY;
    public final float positionZ;

    public PosedCuboid(final AdvancedModelRenderer cuboid) {
        this.rotationX = cuboid.defaultRotationX;
        this.rotationY = cuboid.defaultRotationY;
        this.rotationZ = cuboid.defaultRotationZ;

        this.positionX = cuboid.defaultPositionX;
        this.positionY = cuboid.defaultPositionY;
        this.positionZ = cuboid.defaultPositionZ;
    }
}
