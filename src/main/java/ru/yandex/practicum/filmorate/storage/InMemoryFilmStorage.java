package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Data
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films;
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
        }
    }

    public void deleteFilm(Film film) {
        films.remove(film.getId());
    }

    public Optional<Film> searchFilm(Integer filmId) {
        log.info("dВОЗВРАТ ДАННОГО фильма {}", films.get(filmId));
        return Optional.ofNullable(films.get(filmId));
    }

    public List<Film> getSortFilms() {
        List<Film> film = new ArrayList<>();
        for (Map.Entry<Integer, Film> filmId : films.entrySet()) {
            filmId.getValue().counterRatingFilm();
            film.add(filmId.getValue());
        }
        film.sort((a, b) -> b.getRatingFilm() - a.getRatingFilm());
        log.info("отсортирован фильм {}", film);
        return film;
    }

    public Collection<Film> returnListFilm() {
        log.info("Возврат списка фильмов");
        return films.values();
    }
}
