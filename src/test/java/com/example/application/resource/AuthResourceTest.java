package com.example.application.resource;

import com.example.application.model.request.AuthRequest;
import com.example.config.PostgresResource;
import com.example.utility.ModelGenerator;
import com.example.utility.ValueGenerator;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import static com.example.utility.JsonUtility.convertObjectToJson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class AuthResourceTest {

    @Test
    void Login_Credentials_SUCCESS() {

        var createdAccount = ModelGenerator.createdAccount(1L);
        given()
                .body(convertObjectToJson(
                        createAuthRequest(createdAccount.accountName(), "password")
                ))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/auth/login")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body(
                        "token", notNullValue()
                );
    }

    @Test
    void Login_Credentials_Failure_Unauthorized() {
        var createdAccount = ModelGenerator.createdAccount(2L);
        given()
                .body(convertObjectToJson(
                        createAuthRequest(createdAccount.accountName(), ValueGenerator.generateString(10))
                ))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/auth/login")
                .then()
                .statusCode(RestResponse.StatusCode.UNAUTHORIZED);
    }

    private AuthRequest createAuthRequest(String accountName, String password) {
        return new AuthRequest(accountName, password);
    }
}