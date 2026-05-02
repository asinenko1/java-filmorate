package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    private UserController controller;
    private User user;

    @BeforeEach
    void beforeEach() {
        controller = new UserController();

        user = new User();
        user.setName("User name");
        user.setBirthday(LocalDate.of(2001,1,15));
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

        assertThrows(ValidationException.class, () -> controller.create(user));
    }

    @Test
    void shouldNotCreateUserWithEmptyEmail() {
        user.setEmail("");

        assertThrows(ValidationException.class, () -> controller.create(user));
    }

    @Test
    void shouldNotCreateUserWithWrongEmail() {
        user.setEmail("email.com");

        assertThrows(ValidationException.class, () -> controller.create(user));
    }

    @Test
    void shouldNotCreateUserWithLoginWithSpaces() {
        user.setLogin("My login");

        assertThrows(ValidationException.class, () -> controller.create(user));
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

        assertThrows(ValidationException.class, () -> controller.create(user));
    }

    @Test
    void shouldNotUpdateUnknownUser() {
        user.setId(12);

        assertThrows(ValidationException.class, () -> controller.update(user));
    }

}
