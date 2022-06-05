package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

public class ValidatorUser {
    private final User user;

    public ValidatorUser(User user) {
        this.user=user;
    }

    public void generationException(){
        calculationMailException();
        calculationLoginException();
        replacingTheNameWithLogin();
        calculationDateOfBerthException();
    }

    private void calculationMailException(){
        if(user.getEmail().isEmpty() || !user.getEmail().contains("@")){
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
    }

    private void calculationLoginException(){
        if(user.getLogin().isEmpty() || !user.getLogin().contains(" ")){
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
    }

    private void replacingTheNameWithLogin(){
        if(user.getName().isEmpty()){
            user.setName(user.getLogin());
        }
    }

    private void calculationDateOfBerthException(){
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }
}
