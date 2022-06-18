package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserStorage {

    private final Set<Film> listFilms = new HashSet<>();
    
}
