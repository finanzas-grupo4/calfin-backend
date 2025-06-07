package com.calfin.calfin_app.bondManagement.application.internal.QueryService;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetAllBondsQuery;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetBondsByUserIdQuery;
import com.calfin.calfin_app.bondManagement.domain.services.BondQueryService;
import com.calfin.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.BondRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BondQueryServiceImpl implements BondQueryService {
    private final BondRepository bondRepository;

    public BondQueryServiceImpl(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    @Override
    public List<Bond> handle(GetAllBondsQuery query) {
        return List.of();
    }

    @Override
    public List<Bond> handle(GetBondsByUserIdQuery query) {
        return List.of();
    }

    @Override
    public Optional<Bond> handle(GetBondByIdQuery query) {
        return Optional.empty();
    }
}
