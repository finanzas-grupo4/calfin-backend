package pe.edu.upc.calfinapp.calfin_app.iam.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.aggregates.User;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.services.UserCommandService;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.services.UserQueryService;
import pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.resources.SignInResource;
import pe.edu.upc.calfinapp.calfin_app.iam.interfaces.rest.resources.SignUpResource;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserCommandService userCommandService;

    @MockBean
    private UserQueryService userQueryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSignUp() throws Exception {
        // Arrange
        SignUpResource signUpResource = new SignUpResource(
                "testuser",
                "password123",
                List.of("ROLE_USER")
        );

        User mockUser = Mockito.mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getUsername()).thenReturn("testuser");
        when(mockUser.getPassword()).thenReturn("hashedpassword123");
        doReturn(Optional.of(mockUser)).when(userCommandService).handle(any(SignUpCommand.class));


        // Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpResource)))
                .andExpect(status().isCreated());
    }


    @Test
    public void testSignIn() throws Exception {
        // Arrange
        SignInResource signInResource = new SignInResource("testuser", "password123");

        User mockUser = Mockito.mock(User.class);
        when(mockUser.getUsername()).thenReturn("testuser");

        String token = "fake-jwt-token";
        ImmutablePair<User, String> authPair = new ImmutablePair<>(mockUser, token);
        doReturn(Optional.of(authPair)).when(userCommandService).handle(any(SignInCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInResource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        User mockUser = Mockito.mock(User.class);
        List<User> userList = List.of(mockUser);

        doReturn(userList).when(userQueryService).handle(any(GetAllUsersQuery.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }
}