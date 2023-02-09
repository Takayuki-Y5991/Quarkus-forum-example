package com.example.application.resource;

import com.example.application.model.request.AccountCreateRequest;
import com.example.application.model.request.AuthRequest;
import com.example.utility.ValueGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import static com.example.utility.JsonUtility.convertObjectToJson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class AuthResourceTest {

    // FIXME: 見直し
    @Test
    void loginValidCredentials() throws JsonProcessingException {

        var source = createRequest();

//        var createdAccount = given()
//                .body(convertObjectToJson(source))
//                .contentType(ContentType.JSON)
//                .when()
//                .post("/v1/account")
//                .then()
//                .statusCode(200);
        given()
                .body(convertObjectToJson(
                        createAuthRequest(source.accountName(), source.password())
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


    private AccountCreateRequest createRequest() {
        return new AccountCreateRequest(
//                ValueGenerator.generateString(10),
                "AdminAccount1",
                ValueGenerator.generateName(),
                ValueGenerator.generateName(),
                ValueGenerator.generateName(),
                ValueGenerator.generateBirthDay(),
                ValueGenerator.generateEmail(),
                ValueGenerator.generateContactNumber(1),
//                ValueGenerator.generateString(10),
                "AdminAccount1",
                1L
        );
    }

    private AuthRequest createAuthRequest(String accountName, String password) {
        return new AuthRequest(accountName, password);
    }
}