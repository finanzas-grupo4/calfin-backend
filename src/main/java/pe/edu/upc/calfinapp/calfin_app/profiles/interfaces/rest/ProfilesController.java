package pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetProfileAByUserIdQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.resources.CreateProfileResource;
import pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.resources.UpdateProfileResource;
import pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import pe.edu.upc.calfinapp.calfin_app.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")

public class ProfilesController {
    private final ProfileCommandService commandService;
    private final ProfileQueryService queryService;

    public ProfilesController(ProfileCommandService commandService, ProfileQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody CreateProfileResource resource) {
        var command = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var created = commandService.handle(command);

        return created.map(profile ->
                ResponseEntity.created(URI.create("/api/v1/profiles/" + profile.getId())).body(profile)
        ).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileResource resource) {
        var command = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updated = commandService.handle(command);

        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        commandService.handle(new DeleteProfileCommand(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        var profiles = queryService.handle(new GetAllProfilesQuery());
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        var result = queryService.handle(new GetProfileByIdQuery(id));
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Long userId) {
        var result = queryService.handle(new GetProfileAByUserIdQuery(new UserId(userId).value()));
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
