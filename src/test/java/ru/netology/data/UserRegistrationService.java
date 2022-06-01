package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.experimental.UtilityClass;

import static io.restassured.RestAssured.given;

@Data
@UtilityClass
public class UserRegistrationService {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static UserData registerUser(String locale, UserStatus status) {
        UserData newUser = UserGenerator.generateInfo(locale, status);      //создание рандомного пользователя
        // запрос на сохранение пользователя в БД
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(newUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200);   // код 200 OK

        return newUser;     //возврат созданного пользователя
    }
}
