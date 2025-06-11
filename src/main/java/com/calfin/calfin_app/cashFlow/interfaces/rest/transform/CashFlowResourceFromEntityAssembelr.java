package com.calfin.calfin_app.cashFlow.interfaces.rest.transform;

import com.calfin.calfin_app.cashFlow.domain.model.aggregates.CashFlow;
import com.calfin.calfin_app.cashFlow.interfaces.rest.resources.CashFlowResource;

public class CashFlowResourceFromEntityAssembelr {
    public static CashFlowResource toResourceFromEntity(CashFlow entity){
        return new CashFlowResource(
            entity.getDate(),
            entity.getBeginningBalance(),
            entity.getInterestPayment(),
            entity.getPrincipalPayment(),
            entity.getTotalPayment(),
            entity.getEndingBalance()
        );
    }
}
