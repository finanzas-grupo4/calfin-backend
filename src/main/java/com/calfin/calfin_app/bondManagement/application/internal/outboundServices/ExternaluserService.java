package com.calfin.calfin_app.bondManagement.application.internal.outboundServices;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.IAM.rest.acl.UsersContextFacade;
import com.calfin.calfin_app.IAM.rest.resources.UserResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternaluserService {
    private final UsersContextFacade usersContextFacade;

    public ExternaluserService(UsersContextFacade usersContextFacade) {
        this.usersContextFacade = usersContextFacade;
    }

    public Optional<Users> fetchUserById(Long userId){
        return this.usersContextFacade.fetchUserById(userId);
    }
}
