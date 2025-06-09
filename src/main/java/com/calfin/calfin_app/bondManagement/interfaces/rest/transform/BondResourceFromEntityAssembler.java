package com.calfin.calfin_app.bondManagement.interfaces.rest.transform;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.*;
import com.calfin.calfin_app.bondManagement.interfaces.rest.resources.BondResource;

import java.util.Date;

public class BondResourceFromEntityAssembler {
    public static BondResource toResourceFromEntity(Bond entity) {
        return new BondResource(
            entity.getId(),
            entity.getBondName(),
            entity.getNominalValue(),
            entity.getCurrency(),
            entity.getTerm(),
            entity.getTermUnit(),
            entity.getInterestRate(),
            entity.isEffectiveRate(),
            entity.getPaymentFrequency(),
            entity.getCompoundingFrequency(),
            entity.getIssueDate(),
            entity.isHasGracePeriod(),
            entity.getGracePeriodType(),
            entity.getGracePeriodLength(),
            entity.getDiscountRate(),
            entity.getUser().getId()
        );
    }
}