package com.gharharyali.org.domain.enumeration;

/**
 * The ProductType enumeration.
 */
public enum ProductType {
    ARTIFICIAL_PLANT,
    SEED,
    INDOOR_PLANT,
    OUT_DOOR_PLANT,
    MAINTAINENCE;

    private String value;

    ProductType() {}

    ProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
