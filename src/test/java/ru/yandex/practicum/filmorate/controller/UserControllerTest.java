package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Marker;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private UserController controller;
    private User user;
    private Validator validator;

    @BeforeEach
    void beforeEach() {
        controller = new UserController();
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        user = new User();
        user.setName("User name");
        user.setBirthday(LocalDate.of(2001, 1, 15));
        user.setLogin("UserLogin");
        user.setEmail("User@email.com");
    }

    @Test
    void shouldCreateUser() {
        User newUser = controller.create(user);

        assertEquals(1, newUser.getId());
        assertEquals("UserLogin", newUser.getLogin());
    }

    @Test
    void shouldNotCreateUserWithEmptyLogin() {
        user.setLogin("");

        var violations = validator.validate(user, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserWithEmptyEmail() {
        user.setEmail("");

        var violations = validator.validate(user, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserWithWrongEmail() {
        user.setEmail("email.com");

        var violations = validator.validate(user, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserWithLoginWithSpaces() {
        user.setLogin("My login");

        var violations = validator.validate(user, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldUseLoginAsNameIfNameIsEmpty() {
        user.setName("");

        User newUser = controller.create(user);

        assertEquals(user.getLogin(), newUser.getName());
    }

    @Test
    void shouldNotCreateUserWithBirthdayInFuture() {
        user.setBirthday(LocalDate.now().plusYears(3));

        var violations = validator.validate(user, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotUpdateUnknownUser() {
        user.setId(12);

        assertThrows(NotFoundException.class, () -> controller.update(user));
    }

}
