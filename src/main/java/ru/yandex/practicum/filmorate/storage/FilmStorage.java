package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    void createFilm(Film film); // добавление фильма

    void updateFilm(Film film); // обновление фильма

    void deleteFilm(Film film); // удаление фильма

    Collection<Film> returnListFilm(); // получение списка фильмов
    Optional<Film> searchFilm(Integer filmId); // поиск фильма в списке

    List<Film> getSortFilms(); // возврат отсортированных фильмов
}
