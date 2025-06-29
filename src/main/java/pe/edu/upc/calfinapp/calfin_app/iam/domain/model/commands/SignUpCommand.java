package pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands;

import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
