package com.calfin.calfin_app.bondManagement.domain.services;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByBondId;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByIdQuery;

import java.util.Optional;

public interface CashFlowsQueryService {
    Optional<CashFlows> handle(GetCashFlowsByIdQuery query);
    Optional<CashFlows> handle(GetCashFlowsByBondId query);
}
