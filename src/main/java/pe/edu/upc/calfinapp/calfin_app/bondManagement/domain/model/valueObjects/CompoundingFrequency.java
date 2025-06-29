package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects;

public enum CompoundingFrequency {
    MENSUAL("Mensual"),
    TRIMESTRAL("Trimestral"),
    SEMESTRAL("Semestral"),
    ANUAL("Anual"),
    CONTINUO("Continuo"),
    NONE("None");// No compounding frequency

    private final String description;

    CompoundingFrequency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
