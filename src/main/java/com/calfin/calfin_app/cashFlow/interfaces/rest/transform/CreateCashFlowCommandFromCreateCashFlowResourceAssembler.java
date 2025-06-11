package com.calfin.calfin_app.cashFlow.interfaces.rest.transform;

import com.calfin.calfin_app.cashFlow.domain.model.commands.CreateCashFlowCommand;
import com.calfin.calfin_app.cashFlow.interfaces.rest.resources.CreateCashFlowResource;

import java.util.Date;

public class CreateCashFlowCommandFromCreateCashFlowResourceAssembler {
    public static CreateCashFlowCommand toCommandFromResource(CreateCashFlowResource resource){
        return new CreateCashFlowCommand(
            resource.date(),
            resource.beginningBalance(),
            resource.interestPayment(),
            resource.principalPayment(),
            resource.totalPayment(),
            resource.endingBalance()
        );
    }
}
