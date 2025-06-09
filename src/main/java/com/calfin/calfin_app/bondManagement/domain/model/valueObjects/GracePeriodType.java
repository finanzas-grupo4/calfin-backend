package com.calfin.calfin_app.bondManagement.domain.model.valueObjects;

public enum GracePeriodType {
    PARCIAL("Parcial"),
    TOTAL("Total");

    private final String description;

    GracePeriodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
