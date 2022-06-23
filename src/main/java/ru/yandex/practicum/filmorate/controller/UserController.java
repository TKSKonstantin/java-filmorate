package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.model.ValidatorUser;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserStorage inMemoryUserStorage;
    private final ValidatorUser validatorUser = new ValidatorUser();

    @GetMapping("/{id}")
    public User getUserId(@PathVariable Integer id) {
        return inMemoryUserStorage.searchUsers(id).orElseThrow(NotFoundObjectException::new);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validatorUser.generationException(user);
        inMemoryUserStorage.addUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validatorUser.generationException(user);
        inMemoryUserStorage.updateUser(user);
        return user;
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user) {
        inMemoryUserStorage.deleteUser(user);
    }

    @GetMapping
    public Collection<User> returnListUser() {
        return inMemoryUserStorage.returnListUser();
    }

    @GetMapping("/{id}/friends")
    public List<User> returnListOfFriends(@PathVariable Integer id) {
        return userService.returnListOfFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> returnSharedFriendsList(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.returnSharedFriendsList(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendsList(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.addFriendsList(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriendsList(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.deleteFriendsList(id, friendId);
    }
}