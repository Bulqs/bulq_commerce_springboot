package com.bulq.bulq_commerce.util.constants;

public enum DeliveryType {
    HOME("HOME DELIVERY"),////pending, approved, rejected
    PICKUP("PICK UP AT OUR NEAREST STORE");//,'pending'

    private final String deliveryType;
    
    // Constructor to assign the string value to each enum constant
    DeliveryType(String deliveryType){
        this.deliveryType = deliveryType;
    }

    // Getter to access the description
    public String getDescription(){
        return deliveryType;
    }

    // Static method to map a string from the payload to the enum constant
    public static DeliveryType fromValue(String value) {
        for (DeliveryType status : DeliveryType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
