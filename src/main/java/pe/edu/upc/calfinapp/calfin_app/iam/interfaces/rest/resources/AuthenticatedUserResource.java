package pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String token) {
}
