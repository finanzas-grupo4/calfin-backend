package com.calfin.calfin_app.bondManagement.interfaces.rest.resources;

import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.*;

import java.util.Date;

public record UpdateBondResource(
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
        Float discountRate
) {
}
