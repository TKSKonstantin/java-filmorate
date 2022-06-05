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

        ValidationException e = assertThrows(ValidationException.class, () -> userController.addUser(user));
        String str = "The email cannot be empty and must contain the character @";
        assertEquals(str, e.getMessage());

        user = new User(2, "Ivanovmail.ru", "Ivanov", LocalDate.of(1990, 2, 23));
        e = assertThrows(ValidationException.class, () -> userController.addUser(user));
        assertEquals(str, e.getMessage());
    }

    @Test
    void addingUserEmptyLogin() {
        user = new User(2, "Ivanov@mail.ru", "Iva nov", LocalDate.of(1990, 2, 23));
        ValidationException e = assertThrows(ValidationException.class, () -> userController.addUser(user));
        assertEquals("The login cannot be empty and contain spaces", e.getMessage());

        user = new User(2, "Ivanov@mail.ru", "", LocalDate.of(1990, 2, 23));

        e = assertThrows(ValidationException.class, () -> userController.addUser(user));
        assertEquals("The login cannot be empty and contain spaces", e.getMessage());
    }

    @Test
    void addingUserEmptyName() {
        user = new User(27, "Ivanov@mail.ru", "Ivanov", LocalDate.of(2022, 6, 5));

        assertNull(user.getName());

        user=userController.addUser(user);

        assertEquals(user.getName(),user.getLogin());
    }

    @Test
    void addingUserDateBirths() {
        user = new User(27, "Ivanov@mail.ru", "Ivanov", LocalDate.of(2022, 6, 6));

        ValidationException e = assertThrows(ValidationException.class, () -> userController.addUser(user));
        assertEquals("The date of birth cannot be in the future", e.getMessage());

        user = new User(27, "Ivanov@mail.ru", "Ivanov", LocalDate.of(2022, 6, 5));

        assertEquals(user, userController.addUser(user));
    }
}
