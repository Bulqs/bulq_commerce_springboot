package com.bulq.bulq_commerce.util.constants;

public enum VerifiedType {
    ISPENDING("VERIFICATION PENDING"),
    ISNINVERIFIED("NIN VERIFIED"),
    ISBVNVERIFIED("BVN VERIFIED"),
    ISFULLYVERIFIED("FULL VERIFICATION");

    private final String verifiedType;
    
    // Constructor to assign the string value to each enum constant
    VerifiedType(String verifiedType){
        this.verifiedType = verifiedType;
    }

    // Getter to access the description
    public String getDescription(){
        return verifiedType;
    }

    // Static method to map a string from the payload to the enum constant
    public static VerifiedType fromValue(String value) {
        for (VerifiedType status : VerifiedType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
