package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.*;

import java.time.LocalDate;
import java.util.Date;

public record CreateBondCommand(
        Long userId,
        String bondName,
        Float additionalCosts,
        Float nominalValue,
        Currency currency,
        Integer term,
        TermUnits termUnit,
        Float interestRate,
        boolean isEffectiveRate,
        PaymentFrequency paymentFrequency,
        CompoundingFrequency compoundingFrequency,
        LocalDate issueDate,
        boolean hasGracePeriod,
        GracePeriodType gracePeriodType,
        Integer gracePeriodLength,
        Float discountRate){}