package com.example.application.resource;

import com.example.application.model.request.AccountUpdateRequest;
import com.example.application.model.response.AccountResponse;
import com.example.config.PostgresResource;
import com.example.domain.repository.AccountRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.example.utility.JsonUtility.convertObjectToJson;
import static com.example.utility.ModelGenerator.createAccountRequest;
import static com.example.utility.ModelGenerator.createdAccount;
import static com.example.utility.ValueGenerator.generateString;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class AccountResourceTest {


    private final AccountRepository accountRepository;

    @Inject
    AccountResourceTest(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    void Account_Create_Success() {
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

    @Test
    @TestSecurity(user = "AdminAccount", roles = "Admin")
    void Account_Fetch_ALL_Success_Some_Count() {
        createdAccount(1L);
        createdAccount(2L);
        var result = given()
                .queryParam("index", 0)
                .queryParam("size", 10)
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/account")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        assertThat(result, not(Collections.EMPTY_LIST));
    }

    @Test
    @TestSecurity(user = "AdminAccount", roles = "Admin")
    void Account_Fetch_ALL_Success_No_Data() {
        createdAccount(1L);
        createdAccount(2L);
        var result = given()
                .queryParam("index", 10)
                .queryParam("size", 10)
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/account")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        assertThat(result, equalTo(Collections.EMPTY_LIST));
    }

    @Test
    @TestSecurity(user = "NormalAccount", roles = "Normal")
    void Account_Fetch_ALL_Failure_Forbidden() {
        given()
                .queryParam("index", 0)
                .queryParam("size", 10)
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/account")
                .then()
                .statusCode(RestResponse.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    void Account_Fetch_ALL_Failure_Unauthorized() {
        given()
                .queryParam("index", 0)
                .queryParam("size", 10)
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/account")
                .then()
                .statusCode(RestResponse.Status.UNAUTHORIZED.getStatusCode());
    }


    @Test
    @TestSecurity(user = "AdminAccount", roles = "Admin")
    void Account_Fetch_BY_ID_Success_Admin_Account() {
        var createdAccount = createdAccount(1L);
        var result = given()
                .when()
                .get("/v1/account/" + createdAccount.id())
                .then()
                .statusCode(RestResponse.Status.OK.getStatusCode())
                .extract()
                .body()
                .as(AccountResponse.class);
        assertThat(result, notNullValue());
    }

    @Test
    @TestSecurity(user = "NormalAccount", roles = "Normal")
    void Account_Fetch_BY_ID_Success_Normal_Account() {
        var result = given()
                .when()
                .get("/v1/account/" + 1)
                .then()
                .statusCode(RestResponse.Status.OK.getStatusCode())
                .extract()
                .body()
                .as(AccountResponse.class);
        assertThat(result, notNullValue());
    }

    @Test
    @TestSecurity(user = "NormalAccount", roles = "Normal")
    void Account_Fetch_BY_ID_Failure_Normal_Account() {
        var createdAccount = createdAccount(1L);
        given()
                .when()
                .get("/v1/account/" + createdAccount.id())
                .then()
                .statusCode(RestResponse.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @TestSecurity(user = "AdminAccount", roles = "Admin")
    void Account_Delete_Success() {
        var createdAccount = createdAccount(2L);
        given()
                .when()
                .delete("/v1/account/" + createdAccount.id())
                .then()
                .statusCode(RestResponse.StatusCode.NO_CONTENT);
    }

    @Test
    @TestSecurity(user = "AdminAccount", roles = "Admin")
    void Account_Update_Success_Admin_Account() {
        var createdAccount = createdAccount(1L);
        var willUpdateAccount = new AccountUpdateRequest(
                createdAccount.id(),
                createdAccount.accountName(),
                createdAccount.firstName(),
                createdAccount.middleName(),
                createdAccount.lastName(),
                createdAccount.birthday(),
                generateString(10).concat("@example.com"),
                createdAccount.phoneNumber(),
                createdAccount.roles().roleId()
        );
        given()
                .body(convertObjectToJson(willUpdateAccount))
                .contentType(ContentType.JSON)
                .when()
                .patch("/v1/account/" + createdAccount.id())
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("email", is(willUpdateAccount.email()))
                .body("email", not(createdAccount.email()));

    }

    @Test
    @TestSecurity(user = "NormalAccount", roles = "Normal")
    void Account_Update_Success_Normal_Account() {
        var createdAccount = given()
                .when()
                .get("/v1/account/" + 1)
                .then()
                .extract()
                .body()
                .as(AccountResponse.class);
        var willUpdateAccount = new AccountUpdateRequest(
                createdAccount.id(),
                createdAccount.accountName(),
                createdAccount.firstName(),
                createdAccount.middleName(),
                createdAccount.lastName(),
                createdAccount.birthday(),
                generateString(10).concat("@example.com"),
                createdAccount.phoneNumber(),
                createdAccount.roles().roleId()
        );
        given()
                .body(convertObjectToJson(willUpdateAccount))
                .contentType(ContentType.JSON)
                .when()
                .patch("/v1/account/" + createdAccount.id())
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("email", is(willUpdateAccount.email()))
                .body("email", not(createdAccount.email()));
    }

    @Test
    @TestSecurity(user = "NormalAccount", roles = "Normal")
    void Account_Update_Failure_Forbidden() {
        var createdAccount = createdAccount(1L);
        var willUpdateAccount = new AccountUpdateRequest(
                createdAccount.id(),
                createdAccount.accountName(),
                createdAccount.firstName(),
                createdAccount.middleName(),
                createdAccount.lastName(),
                createdAccount.birthday(),
                generateString(10).concat("@example.com"),
                createdAccount.phoneNumber(),
                createdAccount.roles().roleId()
        );
        given()
                .body(convertObjectToJson(willUpdateAccount))
                .contentType(ContentType.JSON)
                .when()
                .patch("/v1/account/" + createdAccount.id())
                .then()
                .statusCode(RestResponse.StatusCode.FORBIDDEN);
    }

//    @Test
//    @TestSecurity(user = "NormalAccount", roles = "Normal")
//    void Account_ChangePassword_Success() {
//        var willUpdateAccount = new AccountPasswordChangeRequest(
//                "NormalAccount1",
//                generateString(20)
//        );
//        var updatedAccount = given()
//                .body(convertObjectToJson(willUpdateAccount))
//                .contentType(ContentType.JSON)
//                .when()
//                .patch("/v1/account/self/password")
//                .then()
//                .log().all()
//                .statusCode(RestResponse.StatusCode.OK)
//                .extract()
//                .as(AccountResponse.class);
//        assertThat(updatedAccount, notNullValue());
//    }
}