package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.DeleteCashFlowsCommand;

public interface CashFlowsCommandService {
    Long handle(Bond bond);
    void handle(DeleteCashFlowsCommand command);
}
