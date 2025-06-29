package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.entities.Flows;

import java.util.ArrayList;
import java.util.List;

public record CashFlowsResource(
        Long id,
        Long bondId,
        double precioTeorico,
        double convexidad,
        double duracion,
        double duracionModificada,
        double tcea,
        double trea,
        double precioMaximo,
        List<Flows> flows) {

}
