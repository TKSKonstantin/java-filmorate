package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping("{id}")
    Film getUserId(@PathVariable Integer id) {
        return filmService.searchFilm(id).orElseThrow(NotFoundObjectException::new);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        filmService.createFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping
    public Collection<Film> returnListFilms() {
        return filmService.returnListFilm();
    }

    @DeleteMapping
    public void deleteFilm(@RequestBody Film film) {
        filmService.deleteFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> returnListOfMovieRatings(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmService.returnListOfMovieRatings(count);
    }
}