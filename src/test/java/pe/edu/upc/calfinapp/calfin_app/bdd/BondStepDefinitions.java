package pe.edu.upc.calfinapp.calfin_app.bdd;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.GracePeriodType;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.TermUnits;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.BondCommandService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.BondQueryService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.Currency;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.PaymentFrequency;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.CompoundingFrequency;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = CucumberSpringConfiguration.class)
public class BondStepDefinitions {

    @Autowired
    private BondCommandService bondCommandService;

    @Autowired
    private BondQueryService bondQueryService;

    private Bond createdBond;

    @Given("un bono con nombre {string} y valor nominal de {float}")
    public void un_bono_con_nombre_y_valor_nominal(String name, Float value) {
        Long userId = 1L; // Simulamos un userId

        CreateBondCommand command = new CreateBondCommand(
                userId,
                name,
                value,
                12.0f, // interestRate
                Currency.EUR,
                2, // term
                TermUnits.YEARS,
                1.5f, // discountRate
                true, // effectiveRate
                PaymentFrequency.SEMESTRAL,
                CompoundingFrequency.ANUAL,
                LocalDate.now(),
                true, // hasGracePeriod
                GracePeriodType.TOTAL,
                1, // gracePeriodLength
                50.0f // additionalCosts
        );

        createdBond = bondCommandService.handle(command);
        assertNotNull(createdBond);
    }

    @Then("el bono debe estar registrado en el sistema")
    public void el_bono_debe_estar_registrado_en_el_sistema() {
        var result = bondQueryService.handle(new GetBondByIdQuery(createdBond.getId()));
        assertTrue(result.isPresent());
        assertEquals(createdBond.getId(), result.get().getId());
    }
}

