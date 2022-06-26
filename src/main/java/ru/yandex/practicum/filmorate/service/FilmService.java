package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.ValidatorFilm;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;
    private final ValidatorFilm validatorFilm;

    public void addLike(Integer filmId, Integer userId) {
        if (filmStorage.searchFilm(filmId).isEmpty() || inMemoryUserStorage.searchUsers(userId).isEmpty()) {
            throw new NotFoundObjectException();
        } else {
            filmStorage.searchFilm(filmId).get().getFilmLikeUserId().add(userId);
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (filmStorage.searchFilm(filmId).isEmpty() || inMemoryUserStorage.searchUsers(userId).isEmpty()) {
            throw new NotFoundObjectException();
        } else {
            filmStorage.searchFilm(filmId).get().getFilmLikeUserId().remove(userId);
        }
    }

    public List<Film> returnListOfMovieRatings(int counter) {
        if (filmStorage.getSortFilms().size() < counter) {
            return filmStorage.getSortFilms();
        } else {
            return filmStorage.getSortFilms().subList(0, counter);
        }
    }

    public void createFilm(Film film) {
        validatorFilm.generationException(film);
        filmStorage.createFilm(film);
    }

    public void updateFilm(Film film) {
        validatorFilm.generationException(film);
        filmStorage.updateFilm(film);
    }

    public void deleteFilm(Film film) {
        filmStorage.deleteFilm(film);
    }

    public Collection<Film> returnListFilm() {
        return filmStorage.returnListFilm();
    }

    public Optional<Film> searchFilm(Integer filmId) {
        return filmStorage.searchFilm(filmId);
    }
}
