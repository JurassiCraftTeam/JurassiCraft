package org.jurassicraft.client.model;

import net.ilexiconn.llibrary.client.model.tabula.ITabulaModelAnimator;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.api.Animatable;
import org.jurassicraft.server.entity.AnimalMetadata;

import java.util.Map;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class AnimatableModel extends FixedTabulaModel {

    public AnimatableModel(final TabulaModelContainer model, final AnimalMetadata animal, final ITabulaModelAnimator animator) {
        super(model, animal, animator);
    }

    @Override
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float rotation, final float rotationYaw, final float rotationPitch, final float partialTicks, final Entity entity) {
    	final Animatable animatable = (Animatable) entity;

        if (animatable.isCarcass()) {
            this.setMovementScale(0.0F);
        } else {
            this.setMovementScale(animatable.isSleeping() ? 0.5F : 1.0F);
        }

        super.setRotationAngles(limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks, entity);
    }

    public String[] getCubeIdentifierArray() {
        String[] cubeIdentifiers = new String[this.identifierMap.size()];
        int index = 0;

        final Set<String> identifiers = this.identifierMap.keySet();

        for (String identifier : identifiers) {
            cubeIdentifiers[index] = identifier;
            index++;
        }

        return cubeIdentifiers;
    }

    public String[] getCubeNames() {
        String[] cubeNames = new String[this.cubes.size()];
        int index = 0;

        final Set<String> names = this.cubes.keySet();

        for (String identifier : names) {
            cubeNames[index] = identifier;
            index++;
        }

        return cubeNames;
    }

    public Map<String, AdvancedModelRenderer> getIdentifierCubes() {
        return this.identifierMap;
    }

    @Override
    public void faceTarget(final float yaw, final float pitch, final float rotationDivisor, final AdvancedModelRenderer... boxes) {
    	final float actualRotationDivisor = rotationDivisor * boxes.length;
        final float yawAmount = MathHelper.clamp(MathHelper.wrapDegrees(yaw), -45.0F, 45.0F) / (180.0F / (float) Math.PI) / actualRotationDivisor;
        final float pitchAmount = MathHelper.wrapDegrees(pitch) / (180.0F / (float) Math.PI) / actualRotationDivisor;

        for (AdvancedModelRenderer box : boxes) {
            box.rotateAngleY += yawAmount;
            box.rotateAngleX += pitchAmount;
        }
    }
}