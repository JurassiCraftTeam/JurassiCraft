package org.jurassicraft.client.model.animation.entity;

import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.dinosaur.DinosaurMetadata;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.LegSolverBiped;
import org.jurassicraft.server.entity.LegSolverQuadruped;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.util.math.MathHelper;

public final class LegArticulator {
    private LegArticulator() {}

    public static void articulateBiped(final DinosaurEntity entity, final LegSolverBiped legs, final AdvancedModelRenderer body, final AdvancedModelRenderer leftThigh, final AdvancedModelRenderer leftCalf, final AdvancedModelRenderer rightThigh, final AdvancedModelRenderer rightCalf, final float rotThigh, final float rotCalf, final float delta) {
    	final float heightLeft = legs.left.getHeight(delta);
        final float heightRight = legs.right.getHeight(delta);
        if (heightLeft > 0 || heightRight > 0) {
        	final float sc = LegArticulator.getScale(entity);
            final float avg = LegArticulator.avg(heightLeft, heightRight);
            body.rotationPointY += 16 / sc * avg;
            articulateLegPair(sc, heightLeft, heightRight, avg, 0, leftThigh, leftCalf, rightThigh, rightCalf, rotThigh, rotCalf);
        }
    }

    // front legs must be connected to body
    public static void articulateQuadruped(
    	final DinosaurEntity entity, final LegSolverQuadruped legs, final AdvancedModelRenderer body, final AdvancedModelRenderer neck,
    	final AdvancedModelRenderer backLeftThigh, final AdvancedModelRenderer backLeftCalf,
        final AdvancedModelRenderer backRightThigh, final AdvancedModelRenderer backRightCalf,
        final AdvancedModelRenderer frontLeftThigh, final AdvancedModelRenderer frontLeftCalf,
        final AdvancedModelRenderer frontRightThigh, final AdvancedModelRenderer frontRightCalf,
        final float rotBackThigh, final float rotBackCalf,
        final float rotFrontThigh, final float rotFrontCalf,
        final float delta)
    {
    	final float heightBackLeft = legs.backLeft.getHeight(delta);
    	final float heightBackRight = legs.backRight.getHeight(delta);
    	final float heightFrontLeft = legs.frontLeft.getHeight(delta);
    	final float heightFrontRight = legs.frontRight.getHeight(delta);
        if (heightBackLeft > 0 || heightBackRight > 0 || heightFrontLeft > 0 || heightFrontRight > 0) {
        	final float sc = LegArticulator.getScale(entity);
        	final float backAvg = LegArticulator.avg(heightBackLeft, heightBackRight);
        	final float frontAvg = LegArticulator.avg(heightFrontLeft, heightFrontRight);
        	final float bodyLength = Math.abs(avg(legs.backLeft.forward, legs.backRight.forward) - avg(legs.frontLeft.forward, legs.frontRight.forward));
        	final float tilt = (float) (MathHelper.atan2(bodyLength * sc, backAvg - frontAvg) - Math.PI / 2);
            body.rotationPointY += 16 / sc * backAvg;
            body.rotateAngleX += tilt;
            frontLeftThigh.rotateAngleX -= tilt;
            frontRightThigh.rotateAngleX -= tilt;
            neck.rotateAngleX -= tilt;
            LegArticulator.articulateLegPair(sc, heightBackLeft, heightBackRight, backAvg, 0, backLeftThigh, backLeftCalf, backRightThigh, backRightCalf, rotBackThigh, rotBackCalf);
            LegArticulator.articulateLegPair(sc, heightFrontLeft, heightFrontRight, frontAvg, -frontAvg, frontLeftThigh, frontLeftCalf, frontRightThigh, frontRightCalf, rotFrontThigh, rotFrontCalf);
        }
    }

    private static void articulateLegPair(final float sc, final float heightLeft, final float heightRight, final float avg, final float offsetY, final AdvancedModelRenderer leftThigh, final AdvancedModelRenderer leftCalf, final AdvancedModelRenderer rightThigh, final AdvancedModelRenderer rightCalf, final float rotThigh, final float rotCalf) {
    	final float difLeft = Math.max(0, heightRight - heightLeft);
    	final float difRight = Math.max(0, heightLeft - heightRight);
        leftThigh.rotationPointY += 16 / sc * (Math.max(heightLeft, avg) + offsetY);
        rightThigh.rotationPointY += 16 / sc * (Math.max(heightRight, avg) + offsetY);
        leftThigh.rotateAngleX -= rotThigh * difLeft;
        leftCalf.rotateAngleX += rotCalf * difLeft;
        rightThigh.rotateAngleX -= rotThigh * difRight;
        rightCalf.rotateAngleX += rotCalf * difRight;
    }

    private static float avg(final float a, final float b) {
        return (a + b) / 2;
    }

    private static float getScale(final DinosaurEntity entity) {
    	final float scaleModifier = entity.getAttributes().getScaleModifier();
    	final DinosaurMetadata meta = entity.getMetadata();
        return (float) entity.interpolate(meta.getScaleInfant(), meta.getScaleAdult()) * scaleModifier;
    }
}
