package com.calfin.calfin_app.cashFlow.interfaces.rest.resources;

import java.util.Date;

public record CashFlowResource(
        Date date,
        Integer beginningBalance,
        Integer interestPayment,
        Integer principalPayment,
        Integer totalPayment,
        Integer endingBalance
) {
}
