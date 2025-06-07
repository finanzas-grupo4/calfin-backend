package com.calfin.calfin_app.IAM.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Users {

    @Id
    private Long id;

    private String username;

    private String password;

}
