package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.ValidatorFilm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> listFilms = new HashMap<>();

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        new ValidatorFilm(film).generationException();
        log.info("Добавление нового фильма");
        listFilms.put(film.getId(), film);
        return listFilms.get(film.getId());
    }

    @PatchMapping
    public Film updateFilm(@RequestBody Film film, @RequestParam(required = false) String id) {
        new ValidatorFilm(film).generationException();
        if (id.equals("null")) {
            throw new ValidationException("Не передан ID фильма", id);
        } else if (listFilms.containsKey(Integer.parseInt(id))) {
            log.info("Обновление списка фильмов по Id");
            listFilms.put(Integer.parseInt(id), film);
        } else {
            throw new ValidationException("Введен не верный формат или фильма с данным ID нет, вы ввели");
        }
        return listFilms.get(film.getId());
    }

    @ResponseBody
    @GetMapping
    public List<Film> returnListFilms() {
        log.info("Возвращение списка фильмов");
        return new ArrayList<>(listFilms.values());
    }
}
