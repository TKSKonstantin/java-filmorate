package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
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
    public User addUser(@RequestBody User user) {
        new ValidatorUser(user).generationException();
        log.info("Добавление нового пользователя");
        listUsers.put(user.getId(), user);
        return listUsers.get(user.getId());
    }

    @PatchMapping
   public User updateUser(@RequestBody User user, @RequestParam(required = false) String id) {
        new ValidatorUser(user).generationException();
        if (id.equals("null")) {
            log.warn("ID пустой");
            throw new ValidationException("Не передан ID пользователя", id);
        } else if (listUsers.containsKey(Integer.parseInt(id))) {
            log.info("Изменение информации о пользователе");
            listUsers.put(Integer.parseInt(id), user);
        } else {
            log.warn("Не верный формат ID или пользователя с таким ID нет, указано{}",id);
            throw new ValidationException("Введен не верный формат или пользователь с таким ID не найден");
        }
        return listUsers.get(user.getId());
    }

    @GetMapping
    public List<User> returnListUser(){
        log.info("Возврат списка пользователей");
        return new ArrayList<>(listUsers.values());
    }
}
