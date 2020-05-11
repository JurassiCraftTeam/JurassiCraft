package org.jurassicraft.server.entity;

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
import org.jurassicraft.client.model.AnimatableModel;
import org.jurassicraft.client.model.animation.PoseHandler;
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public abstract class Animal implements Comparable<Animal> {

    private final AnimalMetadata metadata;
    
    public Animal() {
        this.metadata = this.buildMetadata();
    }
    
    protected abstract AnimalMetadata buildMetadata();

    @Override
    public int hashCode() {
        return this.metadata.getIdentifier().hashCode();
    }

    @Override
    public int compareTo(final Animal dinosaur) {
    	return this.getIdentifier().compareTo(dinosaur.getIdentifier());
    }

    @Override
    public boolean equals(final Object object) {
    	return object instanceof Animal && ((Animal) object).getIdentifier().equals(this.getIdentifier());
    }
    
    public EntityLivingBase construct(final World world) {
        return this.metadata.construct(world);
    }
    
    public AnimalMetadata getMetadata() {
        return this.metadata;
    }
    
    public ResourceLocation getIdentifier() {
        return this.getMetadata().getIdentifier();
    }
    public String getLocalizedName() {
    	final ResourceLocation identifier = this.metadata.getIdentifier();
        return I18n.translateToLocal("entity." + identifier.getResourceDomain() + "." + identifier.getResourcePath() + ".name");
    }
}