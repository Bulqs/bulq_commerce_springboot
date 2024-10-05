package com.bulq.bulq_commerce.util.constants;

public enum ApprovalType {
    APPROVED("KYC APPROVED"),
    FAILED("KYC FAILED");

    private final String approvalType;
    
    // Constructor to assign the string value to each enum constant
    ApprovalType(String approvalType){
        this.approvalType = approvalType;
    }

    // Getter to access the description
    public String getDescription(){
        return approvalType;
    }

    // Static method to map a string from the payload to the enum constant
    public static ApprovalType fromValue(String value) {
        for (ApprovalType status : ApprovalType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
