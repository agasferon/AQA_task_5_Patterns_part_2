package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@AllArgsConstructor
@Data
public class CreateUser {
    public static void setUpUser(User user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addFilter(new ResponseLoggingFilter())
            .log(LogDetail.ALL)
            .build();

    public static User generateNewUser(String language, String status) {
        Faker faker = new Faker(new Locale(language));
        User generateUser = new User(
                faker.name().username(),
                faker.internet().password(8, 9, true, false, true),
                status);
        setUpUser(generateUser);
        return generateUser;
    }

    public static User generateNewUserWithInvalidLogin(String language, String status) {
        Faker faker = new Faker(new Locale(language));
        String name = faker.name().username();
        String password = faker.internet().password(8, 9, true, false, true);
        User generateUser = new User(name, password, status);
        setUpUser(generateUser);
        String newName = faker.name().username();
        generateUser = new User(newName, password, status);
        return generateUser;
    }

    public static User generateNewUserWithInvalidPassword(String language, String status) {
        Faker faker = new Faker(new Locale(language));
        String name = faker.name().username();
        String password = faker.internet().password(8, 9, true, false, true);
        User generateUser = new User(name, password, status);
        setUpUser(generateUser);
        String newPassword = faker.internet().password(8, 9, true, false, true);
        generateUser = new User(name, newPassword, status);
        return generateUser;
    }
}