package com.calfin.calfin_app.bondManagement.domain.services;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetAllBondsQuery;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetBondsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface BondQueryService {
    List<Bond> handle(GetAllBondsQuery query);
    List<Bond> handle(GetBondsByUserIdQuery query);
    Optional<Bond> handle(GetBondByIdQuery query);
}
