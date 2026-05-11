package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Marker;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private FilmController controller;
    private Film film;
    private Validator validator;

    @BeforeEach
    void beforeEach() {
        controller = new FilmController();
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        film = new Film();
        film.setName("Film name");
        film.setDescription("Film description");
        film.setReleaseDate(LocalDate.of(2015, 12, 31));
        film.setDuration(90);
    }

    @Test
    void shouldCreateFilm() {
        Film newFilm = controller.create(film);

        assertEquals(1, newFilm.getId());
        assertEquals("Film description", newFilm.getDescription(), "описания должны совпадать");
    }

    @Test
    void shouldNotCreateFilmWithEmptyName() {
        film.setName("");

        var violations = validator.validate(film, Marker.Create.class);

        assertFalse(violations.isEmpty());

    }

    @Test
    void shouldNotCreateFilmWithLongDescription() {
        film.setDescription(":)".repeat(101));

        var violations = validator.validate(film, Marker.Create.class);

        assertFalse(violations.isEmpty());

    }

    @Test
    void shouldNotCreateFilmWithReleaseDateBefore1895() {
        film.setReleaseDate(LocalDate.of(1700, 3, 15));

        var violations = validator.validate(film, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmWithZeroDuration() {
        film.setDuration(0);

        var violations = validator.validate(film, Marker.Create.class);

        assertFalse(violations.isEmpty());
    }

}
