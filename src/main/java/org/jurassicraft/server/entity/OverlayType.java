package org.jurassicraft.server.entity;


public enum OverlayType {
	
    EYE("eyes"), CLAW("claws"), EYELID("eyelid"), MOUTH("mouth"), NOSTRILS("nostrils"), STRIPES("stripes"), TEETH("teeth"), MALE_PARTS("male_parts", (byte) 0b1);

    public static final OverlayType[] VALUES = OverlayType.values();
    //0b0 = NONE, 0b1 = ONLY MALE, 0b10 = ONLY FEMALE
    
    private final String identifier;
    private final byte onlyGender;

    OverlayType(String identifier) {
        this(identifier, (byte) 0b0);
    }
    
    OverlayType(String identifier, byte onlyGender) {
        this.identifier = identifier;
        this.onlyGender = onlyGender;
    }

    @Override
    public String toString() {
        return this.identifier;
    }
    
    public byte getGenderSpec() {
    	return this.onlyGender;
    }
    
    public boolean isGenderSpecific() {
    	return this.onlyGender != 0b0;
    }

}

