package com.calfin.calfin_app.bondManagement.domain.model.valueObjects;

public enum TermUnits {
    MONTHS("MONTHS"),
    YEARS("YEARS");

    private final String value;

    TermUnits(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
