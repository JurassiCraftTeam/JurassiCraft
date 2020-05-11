package org.jurassicraft.server.entity;

import org.jurassicraft.server.util.LangUtils;

public enum GrowthStage {
        ADULT("adult"), INFANT("infant"), JUVENILE("juvenile"), /*FLUORESCENT*/ADOLESCENT("adolescent"), SKELETON("skeleton");

    // Enum#values() is not being cached for security reasons. DONT PERFORM CHANGES ON THIS ARRAY
    public static final GrowthStage[] VALUES = GrowthStage.values();
    
    private final String key;
    GrowthStage(String key) {
        this.key = key;
    }
    public String getKey() {
        return this.key;
    }

    public String getLocalization() {
        return LangUtils.translate("growth_stage." + this.name().toLowerCase() + ".name");
    }
}
