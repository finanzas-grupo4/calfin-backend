package pe.edu.upc.calfinapp.calfin_app.iam.domain.services;

import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
