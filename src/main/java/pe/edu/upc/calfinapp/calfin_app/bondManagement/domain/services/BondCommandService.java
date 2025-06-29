package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.UpdateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.DeleteBondCommand;

import java.util.Optional;

public interface BondCommandService {
    Bond handle(CreateBondCommand command);
    void handle(DeleteBondCommand command);
    Optional<Bond> handle(UpdateBondCommand command);
}
