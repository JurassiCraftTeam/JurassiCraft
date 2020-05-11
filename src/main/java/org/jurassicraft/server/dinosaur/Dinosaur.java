package org.jurassicraft.server.dinosaur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.event.ClientEventHandler;
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.PoseHandler;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.api.GrowthStageGenderContainer;
import org.jurassicraft.server.api.Hybrid;
import org.jurassicraft.server.api.SkeletonOverlayContainer;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.GrowthStage;
import org.jurassicraft.server.entity.OverlayType;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.ai.util.MovementType;
import org.jurassicraft.server.period.TimePeriod;
import org.jurassicraft.server.tabula.TabulaModelHelper;
import org.jurassicraft.server.util.LangUtils;
import com.google.common.io.Files;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaCubeContainer;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public abstract class Dinosaur implements Comparable<Dinosaur> {

    private final Map<GrowthStage, ResourceLocation> maleTextures = new EnumMap<>(GrowthStage.class);
    private final Map<GrowthStage, ResourceLocation> femaleTextures = new EnumMap<>(GrowthStage.class);
    private final Map<GrowthStage, TabulaModelContainer> models = new EnumMap<>(GrowthStage.class);
    private final Map<String, TabulaModelContainer> skeletonModels = new HashMap<>();
    private final Map<OverlayType, Map<GrowthStageGenderContainer, ResourceLocation>> overlayTextures = new HashMap<>();
    private final Map<OverlayType, Map<SkeletonOverlayContainer, ResourceLocation>> skeletonOverlays = new HashMap<>();
    private final DinosaurMetadata metadata;
    private boolean shouldRegister = true;
    private PoseHandler<?> poseHandler;
    
    public Dinosaur() {
        this.metadata = this.buildMetadata();
    }
    protected abstract DinosaurMetadata buildMetadata();

    
    public static Matrix4d getParentRotationMatrix(final TabulaModelContainer model, TabulaCubeContainer cube, final boolean includeParents, boolean ignoreSelf, final float rot) {
    	List<TabulaCubeContainer> parentCubes = new ArrayList<>();

        do {
            if (ignoreSelf) {
                ignoreSelf = false;
            } else {
                parentCubes.add(cube);
            }
        }
        while (includeParents && cube.getParentIdentifier() != null && (cube = TabulaModelHelper.getCubeByIdentifier(cube.getParentIdentifier(), model)) != null);

        Matrix4d mat = new Matrix4d();
        mat.setIdentity();
        Matrix4d transform = new Matrix4d();

        transform.rotY(rot / 180 * Math.PI);
        mat.mul(transform);

        for (int i = parentCubes.size() - 1; i >= 0; i--) {
            cube = parentCubes.get(i);
            transform.setIdentity();
            transform.setTranslation(new Vector3d(cube.getPosition()));
            mat.mul(transform);

            final double rotX = cube.getRotation()[0];
            final double rotY = cube.getRotation()[1];
            final double rotZ = cube.getRotation()[2];

            transform.rotZ(rotZ / 180 * Math.PI);
            mat.mul(transform);
            transform.rotY(rotY / 180 * Math.PI);
            mat.mul(transform);
            transform.rotX(rotX / 180 * Math.PI);
            mat.mul(transform);
        }

        return mat;
    }

    private static double[][] getTransformation(final Matrix4d matrix) {
    	final double sinRotationAngleY, cosRotationAngleY, sinRotationAngleX, cosRotationAngleX, sinRotationAngleZ, cosRotationAngleZ;

        sinRotationAngleY = -matrix.m20;
        cosRotationAngleY = Math.sqrt(1 - sinRotationAngleY * sinRotationAngleY);

        if (Math.abs(cosRotationAngleY) > 0.0001) {
            sinRotationAngleX = matrix.m21 / cosRotationAngleY;
            cosRotationAngleX = matrix.m22 / cosRotationAngleY;
            sinRotationAngleZ = matrix.m10 / cosRotationAngleY;
            cosRotationAngleZ = matrix.m00 / cosRotationAngleY;
        } else {
            sinRotationAngleX = -matrix.m12;
            cosRotationAngleX = matrix.m11;
            sinRotationAngleZ = 0;
            cosRotationAngleZ = 1;
        }

        final double rotationAngleX = epsilon(Math.atan2(sinRotationAngleX, cosRotationAngleX)) / Math.PI * 180;
        final double rotationAngleY = epsilon(Math.atan2(sinRotationAngleY, cosRotationAngleY)) / Math.PI * 180;
        final double rotationAngleZ = epsilon(Math.atan2(sinRotationAngleZ, cosRotationAngleZ)) / Math.PI * 180;
        return new double[][] { { epsilon(matrix.m03), epsilon(matrix.m13), epsilon(matrix.m23) }, { rotationAngleX, rotationAngleY, rotationAngleZ } };
    }

    private static double epsilon(final double x) {
        return x < 0 ? x > -0.0001 ? 0 : x : x < 0.0001 ? 0 : x;
    }

    public void init() {

    	for(final OverlayType type : this.metadata.getOverlays()) {
    		
    		if(this.overlayTextures.get(type) == null)
            	this.overlayTextures.put(type, new HashMap<>());
    		if(this.skeletonOverlays.get(type) == null)
            	this.skeletonOverlays.put(type, new HashMap<>());
    	}

        for (final GrowthStage stage : GrowthStage.VALUES) {
            if (this.doesSupportGrowthStage(stage)) {
            	if(stage == GrowthStage.SKELETON) {
            		this.getSkeletonModels().entrySet().stream().forEach(entry -> this.skeletonModels.put(entry.getKey(), entry.getValue()));
            	}else {
            		this.setModelContainer(stage, this.parseModel(stage.getKey()));
            	}
            } else {
            	this.setModelContainer(stage, this.getModelContainer(GrowthStage.ADULT));
            }
        }

        final ResourceLocation identifier = this.getMetadata().getIdentifier();
        final String domain = identifier.getResourceDomain();
        final String name = identifier.getResourcePath();
        final String textureRoot = "textures/entities/" + name + "/";

		if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {

			for (GrowthStage growthStage : GrowthStage.values()) {
				String growthStageName = growthStage.getKey();

				if (!this.doesSupportGrowthStage(growthStage)) {
					growthStageName = GrowthStage.ADULT.getKey();
				}

				this.maleTextures.put(growthStage, new ResourceLocation(domain, textureRoot + name + "_male_" + growthStageName + ".png"));
				this.femaleTextures.put(growthStage, new ResourceLocation(domain, textureRoot + name + "_female_" + growthStageName + ".png"));

				for (OverlayType type : this.metadata.getOverlays()) {
					if (growthStage != GrowthStage.SKELETON) {
						Map<GrowthStageGenderContainer, ResourceLocation> overlay = this.overlayTextures.get(type);
						final ResourceLocation female = new ResourceLocation(domain, textureRoot + name + "_female_" + growthStageName + "_" + type.toString() + ".png");
						final ResourceLocation male = new ResourceLocation(domain, textureRoot + name + "_male_" + growthStageName + "_" + type.toString() + ".png");

						try {
							ClientProxy.MC.getResourceManager().getResource(female).getInputStream();
							ClientProxy.MC.getResourceManager().getResource(male).getInputStream();

							overlay.put(new GrowthStageGenderContainer(growthStage, false), female);
							overlay.put(new GrowthStageGenderContainer(growthStage, true), male);
							this.overlayTextures.put(type, overlay);

						} catch (IOException e) {
						}
					} else {
						Map<SkeletonOverlayContainer, ResourceLocation> overlay = this.skeletonOverlays.get(type);
						final ResourceLocation fresh = new ResourceLocation(domain, textureRoot + name + "_fresh_skeleton_" + type.toString() + ".png");
						final ResourceLocation fossilized = new ResourceLocation(domain, textureRoot + name + "_fossilized_skeleton_" + type.toString() + ".png");

						try {
							ClientProxy.MC.getResourceManager().getResource(fresh).getInputStream();
							ClientProxy.MC.getResourceManager().getResource(fossilized).getInputStream();
							if (!type.isGenderSpecific()) {

								overlay.put(new SkeletonOverlayContainer(false, false), fresh);
								overlay.put(new SkeletonOverlayContainer(true, false), fossilized);
								overlay.put(new SkeletonOverlayContainer(false, true), fresh);
								overlay.put(new SkeletonOverlayContainer(true, true), fossilized);
								this.skeletonOverlays.put(type, overlay);
							} else {

								if (type.getGenderSpec() == 0b1) {
									overlay.put(new SkeletonOverlayContainer(false, true), fresh);
									overlay.put(new SkeletonOverlayContainer(true, true), fossilized);
								} else {
									overlay.put(new SkeletonOverlayContainer(false, false), fresh);
									overlay.put(new SkeletonOverlayContainer(true, false), fossilized);
								}
								this.skeletonOverlays.put(type, overlay);
							}

						} catch (IOException e) {
						}

					}
				}
			}
		}

        this.poseHandler = new PoseHandler(this);
    }

    @Nullable
    protected TabulaModelContainer parseModel(String growthStage) {
    	final ResourceLocation identifier = this.getIdentifier();
    	final String domain = identifier.getResourceDomain();
    	final String path = identifier.getResourcePath();
    	final ResourceLocation location = new ResourceLocation(domain, "models/entities/" + path + "/" + growthStage + "/" + path + "_" + growthStage + "_idle");
        try {
        	return TabulaModelHelper.loadTabulaModel(location);
        } catch (Exception e) {
        	JurassiCraft.getLogger().fatal("Couldn't load model " + location, e);
        }

        return null;
    }

    @Nonnull
    protected HashMap<String, TabulaModelContainer> getSkeletonModels() {
    	HashMap<String, TabulaModelContainer> models = new HashMap<>();
    	final ResourceLocation identifier = this.getIdentifier();
    	final String domain = identifier.getResourceDomain();
    	final String path = identifier.getResourcePath();
    	final ResourceLocation location = new ResourceLocation(domain, "models/entities/" + path + "/skeleton/" + path + "_skeleton_idle");
        try {
        	models.put("idle", TabulaModelHelper.loadTabulaModel(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
		for (final String type : this.getMetadata().skeletonPoses()) {

			final ResourceLocation furtherLocation = new ResourceLocation(domain, "models/entities/" + path + "/skeleton/" + path + "_skeleton_" + type);
			try {
				models.put(type, TabulaModelHelper.loadTabulaModel(furtherLocation));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

        return models;
    }

    public ResourceLocation getMaleTexture(final GrowthStage stage) {
        return this.maleTextures.get(stage);
    }

    public ResourceLocation getFemaleTexture(final GrowthStage stage) {
        return this.femaleTextures.get(stage);
    }

    public void disableRegistry() {
        this.shouldRegister = false;
    }

    public boolean shouldRegister() {
        return this.shouldRegister;
    }

    @Override
    public int hashCode() {
        return this.metadata.getIdentifier().hashCode();
    }

    protected int fromDays(int days) {
        return (days * 24000) / 8;
    }

    @Override
    public int compareTo(final Dinosaur dinosaur) {
    	return this.getIdentifier().compareTo(dinosaur.getIdentifier());
    }
    
    public ResourceLocation getOverlayTextures(final OverlayType type, final DinosaurEntity entity) {
    	final boolean isMale = entity.isMale();
    	if (entity.getGrowthStage() == GrowthStage.SKELETON) {
    		return this.skeletonOverlays.get(type).get(new SkeletonOverlayContainer(entity.getIsFossile(), isMale));
    	}else {
    		return this.overlayTextures.get(type).get(new GrowthStageGenderContainer(entity.getGrowthStage(), isMale));
    	}    
    }

    @Override
    public boolean equals(final Object object) {
    	return object instanceof Dinosaur && ((Dinosaur) object).getIdentifier().equals(this.getIdentifier());
    }

    public double[] getCubePosition(final String cubeName, final GrowthStage stage) {
    	final TabulaModelContainer model = this.getModelContainer(stage);
    	final TabulaCubeContainer cube = TabulaModelHelper.getCubeByName(cubeName, model);

        if (cube != null) {
            return cube.getPosition();
        }

        return new double[] { 0.0, 0.0, 0.0 };
    }

    public double[] getParentedCubePosition(final String cubeName, final GrowthStage stage, final float rot) {
    	final TabulaModelContainer model = this.getModelContainer(stage);
    	final TabulaCubeContainer cube = TabulaModelHelper.getCubeByName(cubeName, model);

        if (cube != null) {
            return getTransformation(getParentRotationMatrix(model, cube, true, false, rot))[0];
        }

        return new double[] { 0.0, 0.0, 0.0 };
    }

    public double[] getHeadPosition(final GrowthStage stage, final float rot) {
    	return this.getParentedCubePosition(this.metadata.getHeadCubeName(), stage, rot);
    }

    public TabulaModelContainer getModelContainer(final GrowthStage stage) {
    	final TabulaModelContainer model = this.models.get(stage);
        if (model == null) {
            return this.models.get(GrowthStage.ADULT);
        }
        return model;
    }
    
    public Map<String, TabulaModelContainer> getSkeletonModel() {
    	final Map<String, TabulaModelContainer> model = this.skeletonModels;
        return model;
    }

    private void setModelContainer(final GrowthStage stage, final TabulaModelContainer model) {
    	this.models.put(stage, model);
    }
    
    public PoseHandler<?> getPoseHandler() {
        return this.poseHandler;
    }
    
    public DinosaurEntity construct(final World world) {
        return this.metadata.construct(world);
    }

    public void applyMeatEffect(EntityPlayer player, boolean cooked) {
    }
    
    public DinosaurMetadata getMetadata() {
        return this.metadata;
    }
    
    public boolean doesSupportGrowthStage(final GrowthStage stage) {
        return stage == GrowthStage.ADULT || stage == GrowthStage.SKELETON;
    }
    
    public List<GrowthStage> getSupportedStages() {
        List<GrowthStage> supportedStages = new ArrayList<>(4);
        for (GrowthStage stage : GrowthStage.VALUES) {
            if (this.doesSupportGrowthStage(stage)) {
                supportedStages.add(stage);
            }
        }
        return supportedStages;
}
    
    public ResourceLocation getIdentifier() {
        return this.getMetadata().getIdentifier();
    }
    
    public String getLocalizedName() {
    	final ResourceLocation identifier = this.metadata.getIdentifier();
        return I18n.translateToLocal("entity." + identifier.getResourceDomain() + "." + identifier.getResourcePath() + ".name");
    }

    public static enum DinosaurType {
        AGGRESSIVE,
        NEUTRAL,
        PASSIVE,
        SCARED
    }

    public static enum BirthType {
        LIVE_BIRTH,
        EGG_LAYING
    }
	
}