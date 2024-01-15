package com.se300.ledger.controller;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class LedgerRestExternalMockControllerTest {
    @Test
    void testGetAccountById() throws JSONException {

        String expectedJson = "{\"address\" : \"1\", \"balance\" : 38}";

        ExtractableResponse<Response> response = RestAssured
                .given()
                .filter(new RequestLoggingFilter())
                .auth().basic("sergey", "chapman")
                .contentType(ContentType.JSON)
                .when()
                .get("https://6546c4c1902874dff3abd4bd.mockapi.io/api/accounts/1")
                .then()
                .statusCode(200)
                .extract();

        JSONAssert.assertEquals(expectedJson, response.body().asPrettyString(),true);
    }

    @Test
    public void testGetTransactionById() throws JSONException {

        //TODO: Implement Transaction External Retrieval Test Method
    }
}
