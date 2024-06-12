package com.dreamsoft.ticketing.entity.enumerations;

public enum Status {

    PENDING("en cours"),

    COMPLETED("terminé"),

    CANCELED("annulé");
    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isValidEnum(String name) {
        try {
            Status.valueOf(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
