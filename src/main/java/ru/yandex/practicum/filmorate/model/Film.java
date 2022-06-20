package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private final Integer id = setId();
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private Set<Integer> filmLikeUserId=new HashSet<>();
    private int ratingFilm=0;
    private static Integer idCreate = 0;
    private static Integer setId() {
        return ++idCreate;
    }

    private void counterRatingFilm(){
        if(!filmLikeUserId.isEmpty()){
            ratingFilm = filmLikeUserId.size();
        }
    }
}