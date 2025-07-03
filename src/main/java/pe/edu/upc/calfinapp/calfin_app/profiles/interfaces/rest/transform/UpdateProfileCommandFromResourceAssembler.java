package pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.transform;


import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long profileId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                profileId,
                resource.email(),
                resource.age()
        );
    }
}
