package pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands;

public record UpdateProfileCommand(Long profileId,
                                   String email,
                                   Integer age) {
}
