package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private FilmController controller;
    private Film film;

    @BeforeEach
    void beforeEach() {
        controller = new FilmController();
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

        assertThrows(ValidationException.class, () -> controller.create(film));
    }

    @Test
    void shouldNotCreateFilmWithLongDescription() {
        film.setDescription(":)".repeat(101));

        assertThrows(ValidationException.class, () -> controller.create(film));
    }

    @Test
    void shouldNotCreateFilmWithReleaseDateBefore1895() {
        film.setReleaseDate(LocalDate.of(1700, 3, 15));

        assertThrows(ValidationException.class, () -> controller.create(film));
    }

    @Test
    void shouldNotCreateFilmWithZeroDuration() {
        film.setDuration(0);

        assertThrows(ValidationException.class, () -> controller.create(film));
    }

}
