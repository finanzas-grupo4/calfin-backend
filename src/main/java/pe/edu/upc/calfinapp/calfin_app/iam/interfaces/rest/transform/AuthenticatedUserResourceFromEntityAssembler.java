package pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.transform;

import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.aggregates.User;
import pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
    return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
  }
}
