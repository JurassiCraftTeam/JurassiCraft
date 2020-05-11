package org.jurassicraft.client.model.animation.dto;

import com.google.gson.Gson;
import org.jurassicraft.client.model.animation.EntityAnimation;

import java.util.Map;

/**
 * This class can be loaded via {@link Gson#fromJson}. It represents the poses of the animations of a model.
 *
 * @author WorldSEnder
 */
public class AnimationsDTO {
    /**
     * Maps an {@link EntityAnimation} as a string to the list of sequential variants
     */
    public Map<String, VariantDTO> poses;
    
    public int version;
}
