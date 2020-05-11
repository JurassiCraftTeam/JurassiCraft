package org.jurassicraft.client.model;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.client.model.tabula.baked.VanillaTabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.baked.deserializer.ItemCameraTransformsDeserializer;
import net.ilexiconn.llibrary.client.model.tabula.baked.deserializer.ItemTransformVec3fDeserializer;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaCubeContainer;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaCubeGroupContainer;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author pau101
 */
@SideOnly(Side.CLIENT)
public enum JurassicraftTabulaModelHandler implements ICustomModelLoader, JsonDeserializationContext {
    INSTANCE;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3fDeserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransformsDeserializer()).create();
    private final JsonParser parser = new JsonParser();
    private final ModelBlock.Deserializer modelBlockDeserializer = new ModelBlock.Deserializer();
    private IResourceManager manager;
    private final Set<String> enabledDomains = new HashSet<>();

    public void addDomain(final String domain) {
        this.enabledDomains.add(domain.toLowerCase(Locale.ROOT));
        LLibrary.LOGGER.info("JurassicraftTabulaModelHandler: Domain {} has been added.", domain.toLowerCase(Locale.ROOT));
    }

    /**
     * Load a {@link TabulaModelContainer} from the path. A slash will be added if it isn't in the path already.
     *
     * @param path the model path
     * @return the new {@link TabulaModelContainer} instance
     * @throws IOException if the file can't be found
     */
    public TabulaModelContainer loadTabulaModel(String path) throws IOException {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.endsWith(".tbl")) {
            path += ".tbl";
        }
        final InputStream stream = net.ilexiconn.llibrary.client.model.tabula.TabulaModelHandler.class.getResourceAsStream(path);
        return INSTANCE.loadTabulaModel(this.getModelJsonStream(path, stream));
    }

    /**
     * Load a {@link TabulaModelContainer} from the model.json input stream.
     *
     * @param stream the model.json input stream
     * @return the new {@link TabulaModelContainer} instance
     */
    public TabulaModelContainer loadTabulaModel(final InputStream stream) {
        return this.gson.fromJson(new InputStreamReader(stream), TabulaModelContainer.class);
    }

    /**
     * @param name  the cube name
     * @param model the model container
     * @return the cube
     */
    public TabulaCubeContainer getCubeByName(final String name, final TabulaModelContainer model) {
    	final List<TabulaCubeContainer> allCubes = this.getAllCubes(model);

        for (TabulaCubeContainer cube : allCubes) {
            if (cube.getName().equals(name)) {
                return cube;
            }
        }

        return null;
    }

    /**
     * @param identifier the cube identifier
     * @param model      the model container
     * @return the cube
     */
    public TabulaCubeContainer getCubeByIdentifier(final String identifier, final TabulaModelContainer model) {
    	final List<TabulaCubeContainer> allCubes = this.getAllCubes(model);

        for (TabulaCubeContainer cube : allCubes) {
            if (cube.getIdentifier().equals(identifier)) {
                return cube;
            }
        }

        return null;
    }

    /**
     * @param model the model container
     * @return an array with all cubes of the model
     */
    public List<TabulaCubeContainer> getAllCubes(final TabulaModelContainer model) {
    	final List<TabulaCubeContainer> cubes = new ArrayList<>();

        for (TabulaCubeGroupContainer cubeGroup : model.getCubeGroups()) {
            cubes.addAll(this.traverse(cubeGroup));
        }

        for (TabulaCubeContainer cube : model.getCubes()) {
            cubes.addAll(this.traverse(cube));
        }

        return cubes;
    }

    private List<TabulaCubeContainer> traverse(final TabulaCubeGroupContainer group) {
    	final List<TabulaCubeContainer> retCubes = new ArrayList<>();

        for (TabulaCubeContainer child : group.getCubes()) {
            retCubes.addAll(this.traverse(child));
        }

        for (TabulaCubeGroupContainer child : group.getCubeGroups()) {
            retCubes.addAll(this.traverse(child));
        }

        return retCubes;
    }

    private List<TabulaCubeContainer> traverse(final TabulaCubeContainer cube) {
    	final List<TabulaCubeContainer> retCubes = new ArrayList<>();

        retCubes.add(cube);

        for (TabulaCubeContainer child : cube.getChildren()) {
            retCubes.addAll(this.traverse(child));
        }

        return retCubes;
    }

    @Override
    public void onResourceManagerReload(final IResourceManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean accepts(final ResourceLocation modelLocation) {
        return this.enabledDomains.contains(modelLocation.getResourceDomain()) && modelLocation.getResourcePath().endsWith(".tbl_jurassicraft");
    }

    @Override
    public IModel loadModel(final ResourceLocation modelLocation) throws IOException {
    	
        String modelPath = modelLocation.getResourcePath();
        modelPath = modelPath.substring(0, modelPath.lastIndexOf('.')) + ".json";
        final IResource resource = this.manager.getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelPath));
        final InputStreamReader jsonStream = new InputStreamReader(resource.getInputStream());
        final JsonElement json = this.parser.parse(jsonStream);
        jsonStream.close();
        final ModelBlock modelBlock = this.modelBlockDeserializer.deserialize(json, ModelBlock.class, this);
        final String tblLocationStr = json.getAsJsonObject().get("tabula").getAsString() + ".tbl";
        final ResourceLocation tblLocation = new ResourceLocation(tblLocationStr);
        final IResource tblResource = this.manager.getResource(tblLocation);
        final InputStream modelStream = this.getModelJsonStream(tblLocation.toString(), tblResource.getInputStream());
        final TabulaModelContainer modelJson = INSTANCE.loadTabulaModel(modelStream);
        modelStream.close();
        final ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
        int layer = 0;
        String texture;
        while ((texture = modelBlock.textures.get("layer" + layer++)) != null) {
            builder.add(new ResourceLocation(texture));
        }
        final String particle = modelBlock.textures.get("particle");
        return new JurassicraftVanillaTabulaModel(modelJson, particle != null ? new ResourceLocation(particle) : null, builder.build(), PerspectiveMapWrapper.getTransforms(modelBlock.getAllTransforms()));
    }

    private InputStream getModelJsonStream(final String name, final InputStream file) throws IOException {
    	final ZipInputStream zip = new ZipInputStream(file);
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals("model.json")) {
                return zip;
            }
        }
        throw new RuntimeException("No model.json present in " + name);
    }

    @Override
    public <T> T deserialize(final JsonElement json, final Type type) throws JsonParseException {
        return this.gson.fromJson(json, type);
    }
}
