package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.CreateBondResource;

public class CreateBondCommandFromResourceAssembler {
    public static CreateBondCommand toCommandFromResource(CreateBondResource resource,Long userId){
        return new CreateBondCommand(
                userId,
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