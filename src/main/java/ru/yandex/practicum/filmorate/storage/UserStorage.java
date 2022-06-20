package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;


public interface UserStorage {

    void addUser(User user); //добавление пользователя

    void updateUser(User user); // обновление пользователя

    void deleteUser(User user); // удаление пользователя

    Collection<User> returnListUser(); // возвращение списка пользователей

    Optional<User> searchUsers(Integer userId); // поиск пользователя в списке

    List<User> getListFriends(Set<Integer> id); // возвращение списка друзей

}
