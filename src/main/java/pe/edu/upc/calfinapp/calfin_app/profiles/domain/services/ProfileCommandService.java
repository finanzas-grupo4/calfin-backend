package pe.edu.upc.calfinapp.calfin_app.profiles.domain.services;



import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    void handle(DeleteProfileCommand command);
}
