package ru.yandex.practicum.filmorate.model;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Slf4j
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
            log.warn("Введено отрицательное значение продолжительности фильма{}",film.getDuration());
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
    }

    private void checkingTheMovieName() {
        if (film.getName().isEmpty()) {
            log.warn("Ввели не верный формат-пустое значение");
            throw new ValidationException("название не может быть пустым");
        }
    }

    private void checkingMaxSizeMovie() {
        if (film.getDescription().length() > 200) {
            log.warn("Вы ввели {} символов, а можно 200",film.getDescription().length());
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
    }

    private void creatingMovieDateException() {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Вы ввели {} эта дата раньше даты 28 декабря 1895 года- день рождения кино",film.getReleaseDate());
            throw new ValidationException("дата релиза — не может быть раньше 28 декабря 1895 года");
        }
    }
}
