package pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.resources;

public record CreateProfileResource(
        String username,
        String password,
        Long userId) {
}
