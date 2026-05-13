package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film findById(Integer id) {
        return filmStorage.getById(id);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь с Id + " + userId + " не найден");
        }

        film.getLikes().add(userId);
        log.info("Пользователь с id {} поставил лайк фильму с id {}", userId, filmId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь с Id + " + userId + " не найден");
        }

        film.getLikes().remove(userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {

        if (count <= 0) {
            throw new ValidationException("Количество фильмов должно быть больше 0");
        }
        return filmStorage.findAll()
                .stream()
                .sorted((film1, film2) -> Integer.compare(
                        film2.getLikes().size(),
                        film1.getLikes().size()
                ))
                .limit(count)
                .collect(Collectors.toList());
    }
}
