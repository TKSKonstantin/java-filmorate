package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.ValidatorUser;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> listUsers = new HashMap<>();

    @PostMapping
    private User addUser(@RequestBody User user) {
        new ValidatorUser(user).generationException();
        listUsers.put(user.getId(), user);
        return listUsers.get(user.getId());
    }

    @PatchMapping
    private User updateUser(@RequestBody User user, @RequestParam(required = false) String userId) {
        new ValidatorUser(user).generationException();
        if (userId.equals("null")) {
            throw new ValidationException("Не передан ID пользователя", userId);
        } else if (listUsers.containsKey(Integer.parseInt(userId))) {
            listUsers.put(Integer.parseInt(userId), user);
        } else {
            throw new ValidationException("Введен не верный формат" +
                    "или пользователь с таким ID не найден, вы ввели", userId);
        }
        return listUsers.get(user.getId());
    }

    @GetMapping
    private List<User> returnListFilms(){
        return (List<User>) listUsers.values();
    }
}
