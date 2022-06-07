package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    private final Integer id = setId();
    private final String email;
    private final String login;
    @NonNull
    private String name;
    private final LocalDate birthday;
    private static Integer idCreate = 0;

    private static Integer setId() {
        return ++idCreate;
    }
}
