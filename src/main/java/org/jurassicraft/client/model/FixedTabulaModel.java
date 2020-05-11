package org.jurassicraft.client.model;

import org.jurassicraft.server.entity.AnimalMetadata;

import net.ilexiconn.llibrary.client.model.tabula.ITabulaModelAnimator;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaCubeContainer;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaCubeGroupContainer;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FixedTabulaModel extends TabulaModel {
	
	private final AnimalMetadata animal;
	
    public FixedTabulaModel(final TabulaModelContainer container, final AnimalMetadata animal, final ITabulaModelAnimator<?> tabulaAnimator) {
        super(container, tabulaAnimator);
        this.animal = animal;
        this.cubes.clear();
        this.identifierMap.clear();
        this.rootBoxes.clear();
        for (TabulaCubeContainer cube : container.getCubes()) {
            this.parseCube(cube, null);
        }
        container.getCubeGroups().forEach(this::parseCubeGroup);
        this.updateDefaultPose();
    }

    private void parseCubeGroup(final TabulaCubeGroupContainer container) {
        for (TabulaCubeContainer cube : container.getCubes()) {
            this.parseCube(cube, null);
        }
        container.getCubeGroups().forEach(this::parseCubeGroup);
    }

    private void parseCube(final TabulaCubeContainer cube, final FixedModelRenderer parent) {
    	final FixedModelRenderer box = this.createBox(cube);
        this.cubes.put(cube.getName(), box);
        this.identifierMap.put(cube.getIdentifier(), box);
        if (parent != null) {
            parent.addChild(box);
        } else {
            this.rootBoxes.add(box);
        }
        for (TabulaCubeContainer child : cube.getChildren()) {
            this.parseCube(child, box);
        }
    }

    private FixedModelRenderer createBox(final TabulaCubeContainer cube) {
    	final int[] textureOffset = cube.getTextureOffset();
        final double[] position = cube.getPosition();
        final double[] rotation = cube.getRotation();
        final double[] offset = cube.getOffset();
        final int[] dimensions = cube.getDimensions();
        final FixedModelRenderer box = new FixedModelRenderer(this, cube.getName(), this.animal);
        box.setTextureOffset(textureOffset[0], textureOffset[1]);
        box.setRotationPoint((float) position[0], (float) position[1], (float) position[2]);
        box.addBox((float) offset[0], (float) offset[1], (float) offset[2], dimensions[0], dimensions[1], dimensions[2], 0.0F);
        box.rotateAngleX = (float) Math.toRadians(rotation[0]);
        box.rotateAngleY = (float) Math.toRadians(rotation[1]);
        box.rotateAngleZ = (float) Math.toRadians(rotation[2]);
        return box;
    }
}
