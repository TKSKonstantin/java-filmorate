package ru.yandex.practicum.filmorate.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Component
@Slf4j
public class ValidatorUser {
    public void generationException(User user) {
        replacingTheNameWithLogin(user);
        calculationMailException(user);
        calculationLoginException(user);
        calculationDateOfBerthException(user);
    }

    private void calculationMailException(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.warn("Не верный формат адреса почты, вы ввели {}", user.getEmail());
            throw new ValidationException("The email cannot be empty and must contain the character @");
        }
    }

    private void calculationLoginException(User user) {
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.warn("Не верный формат логина, вы ввели {}", user.getLogin());
            throw new ValidationException("The login cannot be empty and contain spaces");
        }
    }

    private void replacingTheNameWithLogin(User user) {
        if (user.getName().isBlank()) {
            log.info("имя для отображения может быть пустым" +
                    " — в таком случае будет использован логин {}", user.getLogin());
            user.setName(user.getLogin());
        }
    }

    private void calculationDateOfBerthException(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Сейчас {}, дата рождения не может быть в будущем," +
                    " введена дата {}", LocalDate.now(), user.getBirthday());
            throw new ValidationException("The date of birth cannot be in the future");
        }
    }
}