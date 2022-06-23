package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;

@Service
@Data
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

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
}
