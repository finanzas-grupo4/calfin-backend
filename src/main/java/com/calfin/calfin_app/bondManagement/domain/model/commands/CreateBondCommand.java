package com.calfin.calfin_app.bondManagement.domain.model.commands;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.*;

import java.util.Date;

public record CreateBondCommand(
        String bondName,
        Float nominalValue,
        Currency currency,
        Integer term,
        TermUnits termUnit,
        Float interestRate,
        boolean isEffectiveRate,
        PaymentFrequency paymentFrequency,
        CompoundingFrequency compoundingFrequency,
        Date issueDate,
        boolean hasGracePeriod,
        GracePeriodType gracePeriodType,
        Integer gracePeriodLength,
        Float discountRate,

        Users user){}
