package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.*;

import java.time.LocalDate;
import java.util.Date;

public record BondResource(
        Long id,
        Long UserId,
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
        Float discountRate
) {
}