package pe.edu.upc.calfinapp.calfin_app.profiles.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetProfileAByUserIdQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.calfinapp.calfin_app.profiles.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> handle(GetProfileAByUserIdQuery query) {
        return profileRepository.findByUserId(UserId.of(query.userId()));
    }
}
