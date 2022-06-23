package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.model.ValidatorFilm;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final FilmStorage inMemoryFilmStorage;
    private final ValidatorFilm validatorFilm = new ValidatorFilm();

    @GetMapping("{id}")
    public Film getUserId(@PathVariable Integer id) {
        return inMemoryFilmStorage.searchFilm(id).orElseThrow(NotFoundObjectException::new);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        validatorFilm.generationException(film);
        inMemoryFilmStorage.createFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validatorFilm.generationException(film);
        inMemoryFilmStorage.updateFilm(film);
        return film;
    }

    @GetMapping
    public Collection<Film> returnListFilms() {
        return inMemoryFilmStorage.returnListFilm();
    }

    @DeleteMapping
    public void deleteFilm(@RequestBody Film film) {
        inMemoryFilmStorage.deleteFilm(film);
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
        if (count==null) {
            count=10;
        }
        return filmService.returnListOfMovieRatings(count);
    }
}