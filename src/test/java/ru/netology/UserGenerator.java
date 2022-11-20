package ru.netology;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("ru"));

    private UserGenerator() {
    }

    private static void sendRequest(UserData user) {

        given()
                .spec(requestSpec)
                .body(new UserData(
                        user.getLogin(),
                        user.getPassword(),
                        user.getStatus()
                ))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {

        Faker faker = new Faker(new Locale("ru"));
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {

        Faker faker = new Faker(new Locale("ru"));
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {}
    }

    public static UserData getUser(String status) {
        return new UserData(getRandomLogin(), getRandomPassword(), status);
    }

    public static UserData getRegisteredUser(String status) {
        UserData registeredUser = getUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }
}
