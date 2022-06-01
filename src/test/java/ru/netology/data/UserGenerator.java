package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    public static UserData generateInfo(String locale, UserStatus status) {
        Faker faker = new Faker(new Locale(locale));
        return new UserData(
                faker.name().firstName(),
                faker.internet().password(),
                status
        );
    }

    public static String getSomeLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName();
    }

    public static String getSomePassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.internet().password();
    }
}
