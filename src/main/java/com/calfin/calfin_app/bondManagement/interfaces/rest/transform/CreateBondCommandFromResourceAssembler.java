package com.calfin.calfin_app.bondManagement.interfaces.rest.transform;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.*;
import com.calfin.calfin_app.bondManagement.interfaces.rest.resources.CreateBondResource;

import java.util.Date;

public class CreateBondCommandFromResourceAssembler {
    public static CreateBondCommand toCommandFromResource(Users user, CreateBondResource resource){
        return new CreateBondCommand(
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
                resource.discountRate(),
                user
        );
    }
}