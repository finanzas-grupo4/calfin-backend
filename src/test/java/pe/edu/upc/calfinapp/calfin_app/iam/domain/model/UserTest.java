package pe.edu.upc.calfinapp.calfin_app.iam.domain.model;


import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.aggregates.User;

import org.junit.jupiter.api.Test;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.entities.Role;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.valueobjects.Roles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testUsserCreation(){
        // Arrange
        String username = "testuser";
        String password = "password123";

        // Act
        User user = new User(username, password);

        //Assert
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    public void testAddRole(){
        // Arrange
        User user = new User("testuser", "password123");
        Role role = new Role(Roles.ROLE_USER);

        // Act
        user.addRole(role);

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    public void testAddRoles() {
        // Arrange
        User user = new User("testuser", "password123");
        Role role = new Role(Roles.ROLE_USER);
        List<Role> roles = Arrays.asList(role);

        // Act
        user.addRoles(roles);

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }
    @Test
    public void testUserDetailsSecurityFlags() {
        // Arrange
        User user = new User("testuser", "password123");

        // Assert: comportamiento de seguridad habilitado por defecto
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}
