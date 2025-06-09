package com.calfin.calfin_app.IAM.application.internal.QueryService;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.IAM.domain.model.queries.GetUserByIdQuery;
import com.calfin.calfin_app.IAM.domain.services.UserQueryService;
import com.calfin.calfin_app.IAM.infrastructure.pesistence.jpa.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Users> handle(GetUserByIdQuery query) {
        return this.userRepository.findById(query.userId());
    }
}
