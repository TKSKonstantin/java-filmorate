package ru.yandex.practicum.filmorate.model;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Slf4j
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
            log.warn("Не верный формат адреса почты, вы ввели{}",user.getEmail());
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
    }

    private void calculationLoginException(){
        if(user.getLogin().isEmpty() || !user.getLogin().contains(" ")){
            log.warn("Не верный формат логина, вы ввели{}",user.getLogin());
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
    }

    private void replacingTheNameWithLogin(){
        if(user.getName().isEmpty()){
            log.info("имя для отображения может быть пустым" +
                    " — в таком случае будет использован логин{}",user.getLogin());
            user.setName(user.getLogin());
        }
    }

    private void calculationDateOfBerthException(){
        if(user.getBirthday().isAfter(LocalDate.now())){
            log.warn("Сейчас {}, дата рождения не может быть в будущем," +
                    " введена дата {}",LocalDate.now(),user.getBirthday());
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }
}
