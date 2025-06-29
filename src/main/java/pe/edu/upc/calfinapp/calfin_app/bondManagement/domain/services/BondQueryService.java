package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetAllBondsQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface BondQueryService {
    List<Bond> handle(GetAllBondsQuery query);
    List<Bond> handle(GetBondsByUserIdQuery query);
    Optional<Bond> handle(GetBondByIdQuery query);
}
