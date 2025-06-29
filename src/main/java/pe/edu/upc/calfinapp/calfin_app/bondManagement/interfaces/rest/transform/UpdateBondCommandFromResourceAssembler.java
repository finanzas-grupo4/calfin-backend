package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.UpdateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.*;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.UpdateBondResource;

import java.util.Date;

public class UpdateBondCommandFromResourceAssembler {
    public static UpdateBondCommand toCommandFromResource(Long bondId, UpdateBondResource resource) {
        return new UpdateBondCommand(
                bondId,
                resource.bondName(),
                resource.additionalCosts(),
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
