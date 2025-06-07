package com.calfin.calfin_app.bondManagement.domain.services;

import com.calfin.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.queries.DeleteBondCommand;

public interface BondCommandService {
    Long handle(CreateBondCommand command);
    void handle(DeleteBondCommand command);
}
