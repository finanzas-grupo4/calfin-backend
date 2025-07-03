package pe.edu.upc.calfinapp.calfin_app.profiles.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.calfinapp.calfin_app.profiles.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public Optional<Profile> handle(CreateProfileCommand command) {
        // Asegura que estás usando el value object UserId correctamente
        UserId userId = UserId.of(command.userId().value());

        // Verifica si ya existe un perfil para ese userId
        if (profileRepository.findByUserId(userId).isPresent()) {
            return Optional.empty(); // ya existe perfil para ese userId
        }

        // Crea y guarda el nuevo perfil
        var profile = new Profile(
                command.username(),
                command.password(),
                userId
        );

        return Optional.of(profileRepository.save(profile));
    }

    @Override
    @Transactional
    public Optional<Profile> handle(UpdateProfileCommand command) {
        return profileRepository.findById(command.profileId())
                .map(profile -> {
                    profile.edit(
                            command.email(),
                            command.age()
                    );
                    return profileRepository.save(profile);
                });
    }

    @Override
    @Transactional
    public void handle(DeleteProfileCommand command) {
        profileRepository.deleteById(command.profileId());
    }
}
