package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserControllerTest {

    User user;
    UserController userController;

    @BeforeEach
    public void createUserController() {
        userController = new UserController();
    }

    @Test
    void addingUserIncorrectEmail() {
        user = new User(2, "", "Ivanov", LocalDate.of(1990, 2, 23));

        ValidationException e = assertThrows(ValidationException.class, () -> userController.createUser(user));
        String str = "The email cannot be empty and must contain the character @";
        assertEquals(str, e.getMessage());

        user = new User(2, "Ivanovmail.ru", "Ivanov", LocalDate.of(1990, 2, 23));
        e = assertThrows(ValidationException.class, () -> userController.createUser(user));
        assertEquals(str, e.getMessage());
    }

    @Test
    void addingUserEmptyLogin() {
        user = new User(2, "Ivanov@mail.ru", "Iva nov", LocalDate.of(1990, 2, 23));
        ValidationException e = assertThrows(ValidationException.class, () -> userController.createUser(user));
        assertEquals("The login cannot be empty and contain spaces", e.getMessage());

        user = new User(2, "Ivanov@mail.ru", "", LocalDate.of(1990, 2, 23));

        e = assertThrows(ValidationException.class, () -> userController.createUser(user));
        assertEquals("The login cannot be empty and contain spaces", e.getMessage());
    }

    @Test
    void addingUserEmptyName() {
        user = new User(27, "Ivanov@mail.ru", "Ivanov", LocalDate.of(2022, 6, 5));

        assertNull(user.getName());

        user=userController.createUser(user);

        assertEquals(user.getName(),user.getLogin());
    }

    @Test
    void addingUserDateBirths() {
        LocalDate localDate=LocalDate.now().plusDays(1);
        user = new User(27, "Ivanov@mail.ru", "Ivanov", localDate);

        ValidationException e = assertThrows(ValidationException.class, () -> userController.createUser(user));
        assertEquals("The date of birth cannot be in the future", e.getMessage());

        user = new User(27, "Ivanov@mail.ru", "Ivanov", LocalDate.of(2022, 6, 5));

        assertEquals(user, userController.createUser(user));
    }
}
