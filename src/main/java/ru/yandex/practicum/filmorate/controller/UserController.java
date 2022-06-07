package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.model.ValidatorUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> listUsers = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        new ValidatorUser(user).generationException();
        log.info("Добавление нового пользователя");
        listUsers.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        new ValidatorUser(user).generationException();
        if (user.getId() == null || user.getId() <= 0) {
            log.warn("ID пустой");
            throw new ValidationException("Не передан ID пользователя");
        }
        log.info("Изменение информации о пользователе");
        listUsers.put(user.getId(), user);
        return listUsers.get(user.getId());
    }

    @GetMapping
    public List<User> returnListUser() {
        log.info("Возврат списка пользователей");
        return new ArrayList<>(listUsers.values());
    }
}
