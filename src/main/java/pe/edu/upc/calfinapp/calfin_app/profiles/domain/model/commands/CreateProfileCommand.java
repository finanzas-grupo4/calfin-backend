package pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands;

import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;

public record CreateProfileCommand(String username,
                                   String password,
                                   UserId userId) {
}