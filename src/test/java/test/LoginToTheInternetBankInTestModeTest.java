package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.UserGenerator;
import ru.netology.data.UserRegistrationService;
import ru.netology.data.UserStatus;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginToTheInternetBankInTestModeTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldRegisterNewUserAndLogin() {
        var registeredUser = UserRegistrationService.registerUser("en", UserStatus.active);
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();

        $(".heading").shouldBe(Condition.text("Личный кабинет"));
    }

    @Test
    public void shouldFailedAuthIfUserIsNotRegistered() {
        var user = UserGenerator.generateInfo("ru", UserStatus.active);
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();

        $("[data-test-id=error-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка" +
                        " Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldFailedAuthIfUserIsBlocked() {
        var registeredUser = UserRegistrationService.registerUser("ru", UserStatus.blocked);
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();

        $("[data-test-id=error-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Пользователь заблокирован"));

    }

    @Test
    void shouldFailedAuthIfLoginIsWrong() {
        var registeredUser = UserRegistrationService.registerUser("ru", UserStatus.active);
        $("[data-test-id='login'] input").setValue(UserGenerator.getSomeLogin("ru"));
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();

        $("[data-test-id=error-notification]").shouldHave(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldFailedAuthIfPasswordIsWrong() {
        var registeredUser = UserRegistrationService.registerUser("ru", UserStatus.active);
        $("[data-test-id='login'] input").setValue(UserGenerator.getSomePassword("ru"));
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();

        $("[data-test-id=error-notification]").shouldHave(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

}
