package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects;

public enum GracePeriodType {
    PARCIAL("Parcial"),
    TOTAL("Total"),
    NONE("None");// No grace period

    private final String description;

    GracePeriodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
