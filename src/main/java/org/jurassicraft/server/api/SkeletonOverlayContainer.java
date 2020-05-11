package org.jurassicraft.server.api;

public class SkeletonOverlayContainer {
	
    private boolean isFossilized;
    private boolean isMale;

    public SkeletonOverlayContainer(boolean isFossilized, boolean isMale) {
        this.isFossilized = isFossilized;
        this.isMale = isMale;
    }

    public boolean isFossilized() {
        return this.isFossilized;
    }

    public boolean isMale() {
        return this.isMale;
    }

    @Override
    public int hashCode() {
        return (this.isFossilized ? 1 : 0) * (this.isMale() ? 1 : 0) * 54;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SkeletonOverlayContainer) {
            SkeletonOverlayContainer container = (SkeletonOverlayContainer) object;

            return container.isFossilized == this.isFossilized && container.isMale == this.isMale;
        }

        return false;
    }
}
