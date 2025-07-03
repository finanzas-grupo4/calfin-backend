package pe.edu.upc.calfinapp.calfin_app.profiles.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(UserId userId);
}
