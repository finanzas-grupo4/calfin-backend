package pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.resources;

public record UpdateProfileResource(
        String email,
        Integer age
) {
}