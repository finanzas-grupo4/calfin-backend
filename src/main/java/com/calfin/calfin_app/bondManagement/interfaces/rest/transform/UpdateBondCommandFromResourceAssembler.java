package com.calfin.calfin_app.bondManagement.interfaces.rest.transform;

import com.calfin.calfin_app.bondManagement.domain.model.commands.UpdateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.*;
import com.calfin.calfin_app.bondManagement.interfaces.rest.resources.UpdateBondResource;

import java.util.Date;

public class UpdateBondCommandFromResourceAssembler {
    public static UpdateBondCommand toCommandFromResource(Long bondId, UpdateBondResource resource) {
        return new UpdateBondCommand(
                bondId,
                resource.bondName(),
                resource.nominalValue(),
                resource.currency(),
                resource.term(),
                resource.termUnit(),
                resource.interestRate(),
                resource.isEffectiveRate(),
                resource.paymentFrequency(),
                resource.compoundingFrequency(),
                resource.issueDate(),
                resource.hasGracePeriod(),
                resource.gracePeriodType(),
                resource.gracePeriodLength(),
                resource.discountRate()
        );
    }
}
