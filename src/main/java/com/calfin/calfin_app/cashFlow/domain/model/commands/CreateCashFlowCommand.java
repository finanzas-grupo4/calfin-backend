package com.calfin.calfin_app.cashFlow.domain.model.commands;

import java.util.Date;

public record CreateCashFlowCommand(
        Date date,
        Integer beginningBalance,
        Integer interestPayment,
        Integer principalPayment,
        Integer totalPayment,
        Integer endingBalance) {
}