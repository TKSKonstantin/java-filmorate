package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private Integer id;
    private final Set<Integer> filmLikeUserId = new HashSet<>();
    private Integer ratingFilm = 0;

    public void counterRatingFilm() {
        if (!filmLikeUserId.isEmpty()) {
            ratingFilm = filmLikeUserId.size();
        }
    }
}