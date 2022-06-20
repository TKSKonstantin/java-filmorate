package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Data
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;

   public void addUser(User user) {
       log.info("Добавление нового пользователя");
        users.put(user.getId(), user);
    }

    public void updateUser(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            log.warn("ID пустой");
            throw new ValidationException("Не передан ID пользователя");
        } else if (users.containsKey(user.getId())) {
            log.info("Изменение информации о пользователе");
            users.put(user.getId(), user);
        }
    }

    public void deleteUser(User user) {
        users.remove(user.getId());
    }

    public Optional<User> searchUsers(Integer userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public List<User> getListFriends(Set<Integer> id){
        List<User> userList=new ArrayList<>();
        for(Integer idUser:id){
           if(users.containsKey(idUser)){
              userList.add(users.get(idUser));
           }
        }
        return userList;
    }

    public Collection<User> returnListUser() {
        log.info("Возврат списка пользователей");
        return users.values();
    }
}
