package pe.edu.upc.calfinapp.calfin_app.shared.infrastructure.documentation.openapi.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

  @GetMapping("/")
  public String redirectToSwagger() {
    return "redirect:/swagger-ui/index.html#/";
  }
}