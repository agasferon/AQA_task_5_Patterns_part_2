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

    public static User generateNewUser(String language, boolean active){
        User generateUser = new User();
        Faker faker = new Faker(new Locale(language));
        generateUser.setLogin(faker.name().username());
        generateUser.setPassword(faker.internet().password(8, 9, true, false, true));
        if ((active)) {
            generateUser.setStatus("active");
        } else {
            generateUser.setStatus("blocked");
        }
        setUpUser(generateUser);
        return generateUser;
    }

}