package pe.edu.upc.calfinapp.calfin_app.bondManagement.application.internal.QueryService;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetAllBondsQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondsByUserIdQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.BondQueryService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.BondRepository;
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
        return this.bondRepository.findAll();
    }

    @Override
    public List<Bond> handle(GetBondsByUserIdQuery query) {
        return this.bondRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Bond> handle(GetBondByIdQuery query) {
        return this.bondRepository.findById(query.bondId());
    }
}
