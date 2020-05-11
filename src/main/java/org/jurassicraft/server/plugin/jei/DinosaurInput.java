package org.jurassicraft.server.plugin.jei;

import org.jurassicraft.server.dinosaur.Dinosaur;

public class DinosaurInput {
    public final Dinosaur dinosaur;

    public DinosaurInput(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
    }
    
    public Dinosaur getDinosaur() {
        return this.dinosaur;
    }
}
