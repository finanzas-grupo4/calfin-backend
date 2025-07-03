package pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.transform;

import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;
import pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                resource.username(),
                resource.password(),
                new UserId(resource.userId())
        );
    }
}
