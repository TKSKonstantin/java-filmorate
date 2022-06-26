package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Getter
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private static Integer idCreate = 0;

    private static Integer setId() {
        return ++idCreate;
    }

    public void addUser(User user) {
        log.info("Добавление нового пользователя c Id {}", user.getId());
        user.setId(setId());
        users.put(user.getId(), user);
    }

    public void updateUser(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            log.warn("ID пустой или отрицательный");
            throw new NotFoundObjectException();
        } else if (users.containsKey(user.getId())) {
            log.info("Изменение информации о пользователе");
            users.put(user.getId(), user);
        } else {
            log.warn("Пользователя с данным ID нет");
            throw new NotFoundObjectException();
        }
    }

    public void deleteUser(User user) {
        users.remove(user.getId());
    }

    public Optional<User> searchUsers(Integer userId) {
        log.info("Возвращение данного пользователя {}", users.get(userId));
        return Optional.ofNullable(users.get(userId));
    }

    public List<User> getListFriends(Set<Integer> id) {
        List<User> userList = new ArrayList<>();
        for (Integer idUser : id) {
            if (users.containsKey(idUser)) {
                userList.add(users.get(idUser));
            }
        }
        return userList;
    }

    public Collection<User> returnListUser() {
        log.info("Возврат списка пользователей {}", users.values());
        return users.values();
    }

    public User returnList(Integer id){
        return users.get(id);
    }
}
