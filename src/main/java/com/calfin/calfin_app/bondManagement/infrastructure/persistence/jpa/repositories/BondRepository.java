package com.calfin.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BondRepository extends JpaRepository<Bond, Long> {
    List<Bond> findByUserId(Long userId);
}
