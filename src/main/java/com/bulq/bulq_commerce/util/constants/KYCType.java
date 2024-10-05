package com.bulq.bulq_commerce.util.constants;

public enum KYCType {
    NIN("Verification using NIN"),////pending, approved, rejected
    BVN("Verification using BVN");//'completed'

    private final String kycType;
    
    // Constructor to assign the string value to each enum constant
    KYCType(String kycType){
        this.kycType = kycType;
    }

    // Getter to access the description
    public String getDescription(){
        return kycType;
    }

    // Static method to map a string from the payload to the enum constant
    public static KYCType fromValue(String value) {
        for (KYCType status : KYCType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
