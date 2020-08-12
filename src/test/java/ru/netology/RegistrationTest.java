package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {

    @Test
    void shouldLoginIfUserActive() {
        User randomUser = CreateUser.generateNewUser("EN", "active");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(randomUser.getLogin());
        $("[data-test-id=password] input").setValue(randomUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Личный кабинет")).waitUntil(visible, 15000);
    }

    @Test
    void shouldNotLoginIfUserBlocked() {
        User randomUser = CreateUser.generateNewUser("EN", "blocked");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(randomUser.getLogin());
        $("[data-test-id=password] input").setValue(randomUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).waitUntil(visible, 15000);
    }

    @Test
    void shouldNotLoginIfUserInvalid() {
        User randomUser = CreateUser.generateNewUserWithInvalidLogin("EN", "active");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(randomUser.getLogin());
        $("[data-test-id=password] input").setValue(randomUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @Test
    void shouldNotLoginIfPasswordInvalid() {
        User randomUser = CreateUser.generateNewUserWithInvalidPassword("EN", "active");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(randomUser.getLogin());
        $("[data-test-id=password] input").setValue(randomUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }
}