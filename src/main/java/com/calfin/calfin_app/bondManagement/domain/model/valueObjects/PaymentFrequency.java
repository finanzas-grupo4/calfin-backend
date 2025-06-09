package com.calfin.calfin_app.bondManagement.domain.model.valueObjects;

public enum PaymentFrequency {
    MENSUAL("Mensual"),
    TRIMESTRAL("Trimestral"),
    SEMESTRAL("Semestral"),
    ANUAL("Anual");

    private final String description;
    PaymentFrequency(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
