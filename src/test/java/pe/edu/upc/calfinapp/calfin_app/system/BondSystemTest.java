package pe.edu.upc.calfinapp.calfin_app.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.aggregates.User;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.security.SecurityUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BondSystemTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private String authToken;
    private Long createdBondId;
    private Long userId;

    private String username;
    private final String password = "password123";

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Username único
        username = "testuser_" + System.currentTimeMillis();

        // Registro
        SignUpCommand signUpCommand = new SignUpCommand(username, password, null);

        MvcResult signUpResult = mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpCommand)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(username))
                .andReturn();

        Map<String, Object> signUpResponse = objectMapper.readValue(signUpResult.getResponse().getContentAsString(), HashMap.class);
        userId = ((Number) signUpResponse.get("id")).longValue();

        // Login
        SignInCommand signInCommand = new SignInCommand(username, password);

        MvcResult result = mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        authToken = (String) response.get("token");

        assertNotNull(authToken);
        assertNotNull(userId);
    }

    @Test
    public void testBondLifecycle() throws Exception {
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = Mockito.mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUserId).thenReturn(userId);

            // 1. Crear Bond
            Map<String, Object> bondData = new HashMap<>();
            bondData.put("bondName", "Bono Test");
            bondData.put("additionalCosts", 100.0);
            bondData.put("nominalValue", 1000.0);
            bondData.put("currency", "USD");
            bondData.put("term", 5);
            bondData.put("termUnit", "YEARS");
            bondData.put("interestRate", 5.0);
            bondData.put("isEffectiveRate", true);
            bondData.put("paymentFrequency", "SEMESTRAL");
            bondData.put("compoundingFrequency", "ANUAL");
            bondData.put("issueDate", LocalDate.now().toString());
            bondData.put("hasGracePeriod", false);
            bondData.put("gracePeriodType", "NONE");
            bondData.put("gracePeriodLength", 0);
            bondData.put("discountRate", 4.0);

            MvcResult createResult = mockMvc.perform(post("/api/v1/bonds/create-bonds")
                            .header("Authorization", "Bearer " + authToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(bondData)))
                    .andExpect(status().isCreated())
                    .andReturn();

            Map<String, Object> createdBond = objectMapper.readValue(
                    createResult.getResponse().getContentAsString(), HashMap.class);

            createdBondId = ((Number) createdBond.get("id")).longValue();
            assertNotNull(createdBondId);

            // 2. Obtener bond por ID
            mockMvc.perform(get("/api/v1/bonds/{bondId}", createdBondId)
                            .header("Authorization", "Bearer " + authToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.bondName").value("Bono Test"));

            // 3. Obtener todos los Bonds
            mockMvc.perform(get("/api/v1/bonds/all-bonds")
                            .header("Authorization", "Bearer " + authToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[?(@.id == " + createdBondId + ")]").exists());

            // 4. Eliminar bond
            mockMvc.perform(delete("/api/v1/bonds/{bondId}/delete-bond", createdBondId)
                            .header("Authorization", "Bearer " + authToken))
                    .andExpect(status().isOk());

            // 5. Verificar eliminación
            mockMvc.perform(get("/api/v1/bonds/{bondId}", createdBondId)
                            .header("Authorization", "Bearer " + authToken))
                    .andExpect(status().isNotFound());
        }
    }
}
