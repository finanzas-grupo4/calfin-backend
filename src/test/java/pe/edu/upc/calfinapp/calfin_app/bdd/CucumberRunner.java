package pe.edu.upc.calfinapp.calfin_app.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "pe.edu.upc.calfinapp.calfin_app.bdd",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        tags = "not @ignore"
)
public class CucumberRunner {
    // Esta clase actúa como punto de entrada para ejecutar pruebas de Cucumber
} 