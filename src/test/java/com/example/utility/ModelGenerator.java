package com.example.utility;

import com.example.application.model.request.AccountCreateRequest;
import com.example.application.model.response.AccountResponse;
import io.restassured.http.ContentType;

import static com.example.utility.JsonUtility.convertObjectToJson;
import static io.restassured.RestAssured.given;

public class ModelGenerator {
    /**
     * Account 作成モデル
     *
     * @param role 権限
     * @return
     */
    public static AccountCreateRequest createAccountRequest(long role) {
        return new AccountCreateRequest(
                ValueGenerator.generateString(10),
                ValueGenerator.generateName(),
                ValueGenerator.generateName(),
                ValueGenerator.generateName(),
                ValueGenerator.generateBirthDay(),
                ValueGenerator.generateEmail(),
                ValueGenerator.generateContactNumber(1),
                "password",
                role
        );
    }

    /**
     * Account 作成モデル(Integration内部の使用を想定)
     *
     * @param role 権限
     * @return
     */

    public static AccountResponse createdAccount(long role) {
        return given()
                .body(convertObjectToJson(createAccountRequest(role)))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/account")
                .as(AccountResponse.class);
    }
}
