package pe.edu.upc.calfinapp.calfin_app.iam.domain.services;

import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.aggregates.User;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
  Optional<User> handle(SignUpCommand command);
}
