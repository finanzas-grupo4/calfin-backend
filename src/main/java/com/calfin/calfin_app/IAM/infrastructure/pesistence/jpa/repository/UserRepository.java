package com.calfin.calfin_app.IAM.infrastructure.pesistence.jpa.repository;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.IAM.domain.model.queries.GetUserByIdQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    //find by id comes by default
}
