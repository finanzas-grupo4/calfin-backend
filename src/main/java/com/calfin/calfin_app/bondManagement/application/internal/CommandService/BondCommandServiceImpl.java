package com.calfin.calfin_app.bondManagement.application.internal.CommandService;

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
        return 0L;
    }

    @Override
    public void handle(DeleteBondCommand command) {

    }
}
