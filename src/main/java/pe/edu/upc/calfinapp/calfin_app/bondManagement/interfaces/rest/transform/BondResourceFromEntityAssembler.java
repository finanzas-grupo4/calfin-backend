package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.*;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.BondResource;

import java.util.Date;

public class BondResourceFromEntityAssembler {
    public static BondResource toResourceFromEntity(Bond entity) {
        return new BondResource(
                entity.getId(),
                entity.getUserId(),
                entity.getBondName(),
                entity.getAdditionalCosts(),
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
                entity.getDiscountRate()
        );
    }
}