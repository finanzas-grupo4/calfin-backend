package com.calfin.calfin_app.bondManagement.domain.services;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.commands.UpdateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.queries.DeleteBondCommand;

import java.util.Optional;

public interface BondCommandService {
    Long handle(CreateBondCommand command);
    void handle(DeleteBondCommand command);
    Optional<Bond> handle(UpdateBondCommand command);
}
