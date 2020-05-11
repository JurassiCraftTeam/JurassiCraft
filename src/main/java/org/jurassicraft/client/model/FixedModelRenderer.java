package org.jurassicraft.client.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.jurassicraft.server.dinosaur.TyrannosaurusDinosaur;
import org.jurassicraft.server.entity.AnimalMetadata;
import org.jurassicraft.server.entity.dinosaur.TyrannosaurusEntity;

public class FixedModelRenderer extends AdvancedModelRenderer {
    private static final Random RANDOM = new Random();

    private float fixX;
    private float fixY;
    private float fixZ;

    private int displayList;
    private boolean compiled;
    
    public FixedModelRenderer(final AdvancedModelBase model, final String name, final AnimalMetadata animal) {
    	super(model, name);
    	if(animal != null && animal.getOffsetCubes().size() > 0) {
    		final HashMap<String, Float> s = animal.getOffsetCubes();
    		if(s.keySet().stream().anyMatch(name::contains)) {
    			if(!(s.get(name) == null)) {
    				this.fixX = s.get(name);
    			}else {
    				RANDOM.setSeed(name.hashCode() << 16);
    				final float offsetScale = 0.007F;
    				this.fixX = (RANDOM.nextFloat() - 0.5F) * offsetScale;
    			}
            }
    	}
        
    }

    public FixedModelRenderer(final AdvancedModelBase model, final String name) {
        this(model, name, null);
    }

    @Override
    public void render(final float scale) {
        if (!this.isHidden) {
            if (this.showModel) {
                GlStateManager.pushMatrix();
                if (!this.compiled) {
                    this.compileDisplayList(scale);
                }
                GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
                GlStateManager.scale(this.scaleX - this.fixX, this.scaleY, this.scaleZ);
                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                if (this.rotateAngleZ != 0.0F) {
                    GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleZ), 0.0F, 0.0F, 1.0F);
                }
                if (this.rotateAngleY != 0.0F) {
                    GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleY), 0.0F, 1.0F, 0.0F);
                }
                if (this.rotateAngleX != 0.0F) {
                    GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleX), 1.0F, 0.0F, 0.0F);
                }
                if (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F) {
                    GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
                }
                GlStateManager.callList(this.displayList);
                if (this.childModels != null) {
                    for (final ModelRenderer childModel : this.childModels) {
                    	GlStateManager.scale(this.scaleX + this.fixX, this.scaleY, this.scaleZ);
                        childModel.render(scale);
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }

    private void compileDisplayList(final float scale) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, 4864);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        for (final ModelBox box : this.cubeList) {
            box.render(buffer, scale);
        }
        GlStateManager.glEndList();
        this.compiled = true;
    }
}
