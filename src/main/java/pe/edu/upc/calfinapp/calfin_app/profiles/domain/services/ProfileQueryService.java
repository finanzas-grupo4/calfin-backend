package pe.edu.upc.calfinapp.calfin_app.profiles.domain.services;


import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetProfileAByUserIdQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);


    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileAByUserIdQuery query);

}
