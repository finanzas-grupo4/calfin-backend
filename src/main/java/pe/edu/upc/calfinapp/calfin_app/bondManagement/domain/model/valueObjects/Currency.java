package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    JPY("JPY"),
    CNY("CNY");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
