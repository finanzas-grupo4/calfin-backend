package pe.edu.upc.calfinapp.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashFlowsRepository extends JpaRepository<CashFlows, Long> {
    Optional<CashFlows> findByBondId(Long bondId);
}
