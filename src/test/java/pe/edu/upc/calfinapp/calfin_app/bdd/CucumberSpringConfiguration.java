package pe.edu.upc.calfinapp.calfin_app.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
    // Clase vacía pero necesaria para inicializar Spring correctamente en tests de Cucumber
}

