package com.calfin.calfin_app.bondManagement.application.internal.CommandService;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.queries.DeleteBondCommand;
import com.calfin.calfin_app.bondManagement.domain.services.BondCommandService;
import com.calfin.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.BondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BondCommandServiceImpl implements BondCommandService {
    private final BondRepository bondRepository;

    public BondCommandServiceImpl(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    @Override
    public Long handle(CreateBondCommand command) {
        var bond = new Bond(command);
        try {
            bondRepository.save(bond);
        }catch (Exception e){
            throw new RuntimeException("Error while saving bond: " + e.getMessage());
        }
        return bond.getId();
    }

    @Override
    public void handle(DeleteBondCommand command) {
        var optionalBond = this.bondRepository.findById(command.bondId());
        if (optionalBond.isEmpty()) {
            throw new RuntimeException("Bond not found with ID: " + command.bondId());
        }
        this.bondRepository.deleteById(command.bondId());
    }
}
