package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private final String email;
    private final String login;
    private final LocalDate birthday;
    @NonNull
    private String name;
    private Set<Integer> friendsId=new HashSet<>();
    private Integer id;

    public void addFriendsId(Integer id){
        friendsId.add(id);
    }

    public void deleteFriendsId(Integer id){
        friendsId.remove(id);
    }
}