package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.UserGenerator.*;

public class UserTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSuccessWhenRegisteredActiveUser() {

        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").val(registeredUser.getLogin());
        $("[data-test-id='password'] input").val(registeredUser.getPassword());
        $x(".//button").click();
        $(".heading").should(text("Личный кабинет"));
    }

    @Test
    public void shouldGetErrorNotRegisteredUser() {

        var notRegisteredUser = getUser("active");

        $("[data-test-id='login'] input").val(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").val(notRegisteredUser.getPassword());
        $x(".//button").click();
        $("[data-test-id='error-notification'] [class='notification__content']")
                .should(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfWrongPassword() {

        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $("[data-test-id='login'] input").val(registeredUser.getLogin());
        $("[data-test-id='password'] input").val(wrongPassword);
        $x(".//button").click();
        $("[data-test-id='error-notification'] [class='notification__content']")
                .should(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfBlockedUser() {

        var blockedUser = getRegisteredUser("blocked");

        $("[data-test-id='login'] input").val(blockedUser.getLogin());
        $("[data-test-id='password'] input").val(blockedUser.getPassword());
        $x(".//button").click();
        $("[data-test-id='error-notification'] [class='notification__content']")
                .should(text("Пользователь заблокирован"));
    }

    @Test
    public void shouldGetErrorIfWrongLogin() {

        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $("[data-test-id='login'] input").val(wrongLogin);
        $("[data-test-id='password'] input").val(registeredUser.getPassword());
        $x(".//button").click();
        $("[data-test-id='error-notification'] [class='notification__content']")
                .should(text("Неверно указан логин или пароль"));
    }
}
