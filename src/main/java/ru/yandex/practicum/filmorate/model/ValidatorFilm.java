package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

public class ValidatorFilm {
    private final Film film;

    public ValidatorFilm(Film film) {
        this.film = film;
    }

    public void  generationException(){
        creatingDurationException();
        checkingTheMovieName();
        checkingMaxSizeMovie();
        creatingMovieDateException();
    }

    private void creatingDurationException() {
        if (!film.getDuration().isPositive()) {
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
    }

    private void checkingTheMovieName() {
        if (film.getName().isEmpty()) {
            throw new ValidationException("название не может быть пустым");
        }
    }

    private void checkingMaxSizeMovie() {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
    }

    private void creatingMovieDateException() {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не может быть раньше 28 декабря 1895 года");
        }
    }
}
