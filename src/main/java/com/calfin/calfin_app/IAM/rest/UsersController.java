package com.calfin.calfin_app.IAM.rest;

import com.calfin.calfin_app.IAM.domain.model.queries.GetUserByIdQuery;
import com.calfin.calfin_app.IAM.domain.services.UserQueryService;
import com.calfin.calfin_app.IAM.rest.resources.UserResource;
import com.calfin.calfin_app.IAM.rest.transform.UserResourceFromUserEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    private final UserQueryService userQueryService;

    public UsersController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId){
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var optionalUser = this.userQueryService.handle(getUserByIdQuery);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromUserEntityAssembler.toResourceFromEntity(optionalUser.get());
        return ResponseEntity.ok(userResource);
    }

}
