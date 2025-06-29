package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByBondId;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByIdQuery;

import java.util.Optional;

public interface CashFlowsQueryService {
    Optional<CashFlows> handle(GetCashFlowsByIdQuery query);
    Optional<CashFlows> handle(GetCashFlowsByBondId query);
}
