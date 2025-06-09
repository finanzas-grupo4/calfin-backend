package com.calfin.calfin_app.bondManagement.domain.model.valueObjects;

public enum CompoundingFrequency {
    MENSUAL("Mensual"),
    TRIMESTRAL("Trimestral"),
    SEMESTRAL("Semestral"),
    ANUAL("Anual"),
    CONTINUO("Continuo");

    private final String description;

    CompoundingFrequency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
