package com.calfin.calfin_app.IAM.rest.transform;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.IAM.rest.resources.UserResource;

public class UserResourceFromUserEntityAssembler {
    public static UserResource toResourceFromEntity(Users entity){
        return new UserResource(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword()
        );
    }
}
