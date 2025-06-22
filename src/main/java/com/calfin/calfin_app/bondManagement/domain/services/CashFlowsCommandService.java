package com.calfin.calfin_app.bondManagement.domain.services;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.queries.DeleteCashFlowsCommand;

public interface CashFlowsCommandService {
    Long handle(Bond bond);
    void handle(DeleteCashFlowsCommand command);
}
