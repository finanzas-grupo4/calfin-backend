package com.calfin.calfin_app.IAM.domain.services;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.IAM.domain.model.queries.GetUserByIdQuery;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserQueryService {
    Optional<Users> handle(GetUserByIdQuery query);
}
