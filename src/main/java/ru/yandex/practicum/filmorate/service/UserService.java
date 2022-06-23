package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class UserService {
    private final InMemoryUserStorage userStorage;

    public void addFriendsList(Integer userId, Integer friendsId) {
        if (userStorage.searchUsers(userId).isEmpty() || userStorage.searchUsers(friendsId).isEmpty()) {
            throw new NotFoundObjectException();
        } else {
            userStorage.searchUsers(userId).get().addFriendsId(friendsId);
            userStorage.searchUsers(friendsId).get().addFriendsId(userId);
        }
    }

    public void deleteFriendsList(Integer userId, Integer friendsId) {
        if (userStorage.searchUsers(userId).isEmpty() || userStorage.searchUsers(friendsId).isEmpty()) {
            throw new NotFoundObjectException();
        } else {
            userStorage.searchUsers(userId).get().deleteFriendsId(friendsId);
            userStorage.searchUsers(friendsId).get().deleteFriendsId(userId);
        }
    }

    public List<User> returnListOfFriends(Integer userId) {
        return userStorage.getListFriends(userStorage.searchUsers(userId).orElseThrow(NotFoundObjectException::new).getFriendsId());//бросить в параметр ошибку 404-объект не найден
    }

    public List<User> returnSharedFriendsList(Integer userId, Integer otherId) {
        List<User> user = new ArrayList<>();
        if (userStorage.searchUsers(userId).isEmpty() && userStorage.searchUsers(otherId).isEmpty()) {
            throw new NotFoundObjectException();
        } else if (userStorage.searchUsers(userId).isPresent() && userStorage.searchUsers(otherId).isPresent()) {
            for (Integer idUser : userStorage.searchUsers(userId).get().getFriendsId()) {
                if (userStorage.searchUsers(otherId).get().getFriendsId().contains(idUser)) {
                    user.add(userStorage.getUsers().get(idUser));
                }
            }
        }
        return user;
    }
}
