package ru.yandex.practicum.filmorate.controller;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.model.ValidatorFilm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> listFilms = new HashMap<>();
    ValidatorFilm validatorFilm=new ValidatorFilm();
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        validatorFilm.generationException(film);
        log.info("Добавление нового фильма");
        listFilms.put(film.getId(), film);
        return film;
    }
    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validatorFilm.generationException(film);
        if (film.getId() == null || film.getId() <= 0) {
            throw new ValidationException("Не передан ID фильма");
        }
        log.info("Обновление списка фильмов по Id");
        listFilms.put(film.getId(), film);
        return listFilms.get(film.getId());
    }

    @GetMapping
    public List<Film> returnListFilms() {
        log.info("Возвращение списка фильмов");
        return new ArrayList<>(listFilms.values());
    }
}