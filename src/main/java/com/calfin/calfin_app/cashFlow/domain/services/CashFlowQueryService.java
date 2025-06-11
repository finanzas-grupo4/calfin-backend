package com.calfin.calfin_app.cashFlow.domain.services;

import com.calfin.calfin_app.cashFlow.domain.model.aggregates.CashFlow;
import com.calfin.calfin_app.cashFlow.domain.model.queries.GetCashFlowByIdQuery;

import java.util.Optional;

public interface CashFlowQueryService {
    Optional<CashFlow> handle(GetCashFlowByIdQuery query);
}
