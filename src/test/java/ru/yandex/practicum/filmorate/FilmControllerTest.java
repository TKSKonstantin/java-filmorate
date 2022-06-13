package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

@SpringBootTest
public class FilmControllerTest {
    Film film;
    FilmController filmController;

    @BeforeEach
    public void createFilmController() {
        filmController = new FilmController();
    }

    @Test
    void addingAnUntitledMovie() {
        film = new Film("", "Фильм фантастика",
                LocalDate.of(2001, 12, 24), 120);
        ValidationException e = assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        assertEquals("Название не может быть пустым", e.getMessage());
    }

    @Test
    void addingLongMovieDescription() {
        String description = "экранизация романа Стивена Кинга с Томом Хэнксом в главной роли." +
                " Фильм рассказывает историю тюремного блока «Е», в котором содержатся преступники," +
                " осужденные на смертную казнь.– это коридор, ведущий...";
        film = new Film("Зеленая миля", description,
                LocalDate.of(1999, 12, 9), 120);

        ValidationException e = assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        assertEquals(201, description.length(), "смотрим количество символов в описании фильма");
        assertEquals("Максимальная длина описания — 200 символов", e.getMessage());

        description = "экранизация романа Стивена Кинга с Томом Хэнксом в главной роли." +
                " Фильм рассказывает историю тюремного блока «Е», в котором содержатся преступники," +
                " осужденные на смертную казнь.– это коридор, ведущий..";
        film = new Film("Зеленая миля", description,
                LocalDate.of(2001, 12, 24), 120);

        assertEquals(200, description.length());
        assertEquals(film, filmController.createFilm(film), "граничное значение");
    }

    @Test
    void addingDateImplementationMovie() {
        film = new Film("Зеленая миля", "Фильм мелодрама",
                LocalDate.of(1895, 12, 27), 120);
        ValidationException e = assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        assertEquals("Дата релиза — не может быть раньше 28 декабря 1895 года", e.getMessage());

        film = new Film("Зеленая миля", "Фильм мелодрама",
                LocalDate.of(1895, 12, 28), 120);
        assertEquals(film, filmController.createFilm(film), "граничное значение даты");
    }

    @Test
    void addingDurationMovie() {
        film = new Film("Зеленая миля", "Фильм мелодрама",
                LocalDate.of(1999, 12, 9), -1);
        ValidationException e = assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        assertEquals("Продолжительность фильма должна быть положительной", e.getMessage());

        film = new Film("Зеленая миля", "Фильм мелодрама",
                LocalDate.of(1999, 12, 9), 1);
        assertEquals(film, filmController.createFilm(film), "граничное значение даты");
    }
}