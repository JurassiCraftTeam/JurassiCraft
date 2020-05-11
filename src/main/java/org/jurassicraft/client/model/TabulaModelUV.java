package org.jurassicraft.client.model;

import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;

public class TabulaModelUV extends TabulaModelContainer {
	
    public TabulaModelUV(final TabulaModelContainer container, final int height, final int width) {
        super(container.getName(), container.getAuthor(), width, height, container.getCubes(), container.getProjectVersion());
    }
}
