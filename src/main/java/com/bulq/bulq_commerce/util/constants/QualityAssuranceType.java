package com.bulq.bulq_commerce.util.constants;

public enum QualityAssuranceType {
    PENDING("Awaiting approval"),////pending, approved, rejected
    REJECTED("Rejected Quality"),//'completed'
    APPROVED("Approved Quality");//,'pending'

    private final String qaType;
    
    // Constructor to assign the string value to each enum constant
    QualityAssuranceType(String qaType){
        this.qaType = qaType;
    }

    // Getter to access the description
    public String getDescription(){
        return qaType;
    }

    // Static method to map a string from the payload to the enum constant
    public static QualityAssuranceType fromValue(String value) {
        for (QualityAssuranceType status : QualityAssuranceType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
