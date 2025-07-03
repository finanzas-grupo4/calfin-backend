package pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects;

public record UserId(Long value) {

    public UserId {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("User id cannot be null or negative");
        }
    }

    public static UserId of(Long id) {
        return new UserId(id);
    }

    public static UserId none() {
        return new UserId(0L);
    }
}