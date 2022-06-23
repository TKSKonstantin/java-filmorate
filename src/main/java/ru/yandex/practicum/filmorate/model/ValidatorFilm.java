package ru.yandex.practicum.filmorate.model;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Slf4j
public class ValidatorFilm {
    public void generationException(Film film) {
        creatingDurationException(film);
        checkingTheMovieName(film);
        checkingMaxSizeMovie(film);
        creatingMovieDateException(film);
    }

    private void creatingDurationException(Film film) {
        if (film.getDuration() <= 0) {
            log.warn("Введено отрицательное значение продолжительности фильма {}", film.getDuration());
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    private void checkingTheMovieName(Film film) {
        if (film.getName().isBlank()) {
            log.warn("Ввели не верный формат-пустое значение");
            throw new ValidationException("Название не может быть пустым");
        }
    }

    private void checkingMaxSizeMovie(Film film) {
        if (film.getDescription().length() > 200) {
            log.warn("Вы ввели {} символов, разрешено не более 200", film.getDescription().length());
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
    }

    private void creatingMovieDateException(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Вы ввели {} эта дата раньше даты 28 декабря 1895 года- день рождения кино", film.getReleaseDate());
            throw new ValidationException("Дата релиза — не может быть раньше 28 декабря 1895 года");
        }
    }
}