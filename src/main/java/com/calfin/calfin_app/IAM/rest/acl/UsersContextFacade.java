package com.calfin.calfin_app.IAM.rest.acl;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.IAM.domain.model.queries.GetUserByIdQuery;
import com.calfin.calfin_app.IAM.domain.services.UserQueryService;
import com.calfin.calfin_app.IAM.rest.resources.UserResource;
import com.calfin.calfin_app.IAM.rest.transform.UserResourceFromUserEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersContextFacade {
    private final UserQueryService userQueryService;

    public UsersContextFacade(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    public Optional<Users> fetchUserById(Long userId){
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var optionalUser = this.userQueryService.handle(getUserByIdQuery);
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        return optionalUser;

    }

}
