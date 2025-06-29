package pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(String username, String password, List<String> roles) {
}

