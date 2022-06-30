package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films=new HashMap<>();
    private static Integer idCreate = 0;

    private static Integer setId() {
        return ++idCreate;
    }

    public void createFilm(Film film) {
        log.info("Добавление нового пользователя");
        film.setId(setId());
        films.put(film.getId(), film);
    }

    public void updateFilm(Film film) {
        if (film.getId() == null || film.getId() <= 0) {
            log.warn("ID фильма пустой");
            throw new NotFoundObjectException();
        } else if (films.containsKey(film.getId())) {
            log.info("Обновление списка фильмов по Id");
            films.put(film.getId(), film);
        } else {
            log.warn("Фильма с данным ID нет");
            throw new NotFoundObjectException();
        }
    }

    public void deleteFilm(Film film) {
        films.remove(film.getId());
    }

    public Optional<Film> searchFilm(Integer filmId) {
        log.info("Возврат данного фильма {}", films.get(filmId));
        return Optional.ofNullable(films.get(filmId));
    }

    public List<Film> getSortFilms() {
        films.keySet().stream().map(films::get).forEach(Film::counterRatingFilm);
        return films.keySet().stream()
                .map(films::get)
                .sorted((a, b) -> b.getRatingFilm() - a.getRatingFilm())
                .collect(Collectors.toList());
    }

    public Collection<Film> returnListFilm() {
        log.info("Возврат списка фильмов");
        return films.values();
    }
}
