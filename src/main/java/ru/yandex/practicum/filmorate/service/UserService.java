package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.ValidatorUser;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;
    private final ValidatorUser validatorUser;

    public void addFriendsList(Integer userId, Integer friendsId) {
        userStorage.searchUsers(userId).orElseThrow(NotFoundObjectException::new)
                .addFriendsId(userStorage.searchUsers(friendsId).orElseThrow(NotFoundObjectException::new).getId());
        userStorage.searchUsers(friendsId).orElseThrow(NotFoundObjectException::new)
                .addFriendsId(userStorage.searchUsers(userId).orElseThrow(NotFoundObjectException::new).getId());
    }

    public void deleteFriendsList(Integer userId, Integer friendsId) {
        userStorage.searchUsers(userId).orElseThrow(NotFoundObjectException::new)
                .deleteFriendsId(userStorage.searchUsers(friendsId)
                        .orElseThrow(NotFoundObjectException::new).getId());

        userStorage.searchUsers(friendsId).orElseThrow(NotFoundObjectException::new)
                .deleteFriendsId(userStorage.searchUsers(userId)
                        .orElseThrow(NotFoundObjectException::new).getId());
    }

    public List<User> returnListOfFriends(Integer userId) {
        return userStorage.getListFriends(userStorage.searchUsers(userId)
                .orElseThrow(NotFoundObjectException::new).getFriendsId());
    }

    public List<User> returnSharedFriendsList(Integer userId, Integer otherId) {
        return userStorage.searchUsers(userId).orElseThrow(NotFoundObjectException::new).getFriendsId().stream()
                .filter(userStorage.searchUsers(otherId)
                        .orElseThrow(NotFoundObjectException::new).getFriendsId()::contains)
                .map(userStorage::returnList)
                .collect(Collectors.toList());
    }

    public void addUser(User user) {
        validatorUser.generationException(user);
        userStorage.addUser(user);
    }

    public void updateUser(User user) {
        validatorUser.generationException(user);
        userStorage.updateUser(user);
    }

    public void deleteUser(User user) {
        userStorage.deleteUser(user);
    }

    public Optional<User> searchUsers(Integer userId) {
        return userStorage.searchUsers(userId);
    }

    public Collection<User> returnListUser() {
        return userStorage.returnListUser();
    }
}
