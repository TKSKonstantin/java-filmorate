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
    private Film addFilm(@RequestBody Film film) {
        new ValidatorFilm(film).generationException();
        listFilms.put(film.getId(), film);
        return listFilms.get(film.getId());
    }

    @PatchMapping
    private Film updateFilm(@RequestBody Film film, @RequestParam(required = false) String id) {
        new ValidatorFilm(film).generationException();
        if (id.equals("null")) {
            throw new ValidationException("Не передан ID фильма", id);
        } else if (listFilms.containsKey(Integer.parseInt(id))) {
            listFilms.put(Integer.parseInt(id), film);
        } else {
            throw new ValidationException("Введен не верный формат или фильма с данным ID нет, вы ввели");
        }
        return listFilms.get(film.getId());
    }

    @ResponseBody
    @GetMapping
    private List<Film> returnListFilms() {
        log.info("Я ТУТ");
        return new ArrayList<>(listFilms.values());
    }
}
