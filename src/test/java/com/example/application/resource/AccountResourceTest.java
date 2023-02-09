package com.example.application.resource;

import com.example.config.PostgresResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.example.utility.JsonUtility.convertObjectToJson;
import static com.example.utility.ModelGenerator.createAccountRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class AccountResourceTest {

    @Test
    void createAccount() {
        var source = createAccountRequest(1L);
        given()
                .body(convertObjectToJson(source))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/account")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("password", nullValue());
    }
}