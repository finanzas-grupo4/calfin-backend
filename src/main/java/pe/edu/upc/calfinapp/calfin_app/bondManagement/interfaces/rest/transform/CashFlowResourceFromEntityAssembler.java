package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.entities.Flows;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.CashFlowsResource;

import java.util.List;

public class CashFlowResourceFromEntityAssembler {
    public static CashFlowsResource toResourceFromEntity(CashFlows entity){
        return new CashFlowsResource(
                entity.getId(),
                entity.getBond().getId(),
                entity.getPrecioTeorico(),
                entity.getConvexidad(),
                entity.getDuracion(),
                entity.getDuracionModificada(),
                entity.getTcea(),
                entity.getTrea(),
                entity.getPrecioMaximo(),
                entity.getFlows()
        );
    }
}
