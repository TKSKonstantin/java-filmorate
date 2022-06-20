package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private final Integer id = setId();
    private final String email;
    private final String login;
    private Set<Integer> friendsId=new HashSet<>();
    @NonNull
    private String name;
    private final LocalDate birthday;
    private static Integer idCreate = 0;

    private static Integer setId() {
        return ++idCreate;
    }

    public void addFriendsId(Integer id){
        friendsId.add(id);
    }

    public void deleteFriendsId(Integer id){
        friendsId.remove(id);
    }
}