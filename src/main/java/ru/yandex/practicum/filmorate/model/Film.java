package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Film {
    private final Integer id = setId();
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private static Integer idCreate = 0;
    private static Integer setId() {
        return ++idCreate;
    }
}