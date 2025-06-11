package com.calfin.calfin_app.cashFlow.domain.services;

import com.calfin.calfin_app.cashFlow.domain.model.commands.CreateCashFlowCommand;

public interface CashFlowCommandService {
    Long handle(CreateCashFlowCommand command);

}
