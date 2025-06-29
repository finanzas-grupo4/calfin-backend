package pe.edu.upc.calfinapp.calfin_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CalfinAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalfinAppApplication.class, args);
	}

}
