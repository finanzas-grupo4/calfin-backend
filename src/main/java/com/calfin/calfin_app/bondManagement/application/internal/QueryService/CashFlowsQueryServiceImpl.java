package com.calfin.calfin_app.bondManagement.application.internal.QueryService;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByBondId;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByIdQuery;
import com.calfin.calfin_app.bondManagement.domain.services.CashFlowsQueryService;
import com.calfin.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.CashFlowsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashFlowsQueryServiceImpl implements CashFlowsQueryService {
    private final CashFlowsRepository cashFlowsRepository;

    public CashFlowsQueryServiceImpl(CashFlowsRepository cashFlowsRepository) {
        this.cashFlowsRepository = cashFlowsRepository;
    }

    @Override
    public Optional<CashFlows> handle(GetCashFlowsByIdQuery query) {
        return this.cashFlowsRepository.findById(query.id());
    }

    @Override
    public Optional<CashFlows> handle(GetCashFlowsByBondId query) {
        return this.cashFlowsRepository.findByBondId(query.bondId());
    }
}
