package pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(Long id, String username, List<String> roles) {
}
