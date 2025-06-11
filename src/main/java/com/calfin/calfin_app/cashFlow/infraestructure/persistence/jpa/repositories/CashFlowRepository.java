package com.calfin.calfin_app.cashFlow.infraestructure.persistence.jpa.repositories;

import com.calfin.calfin_app.cashFlow.domain.model.aggregates.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, Long> {

}
