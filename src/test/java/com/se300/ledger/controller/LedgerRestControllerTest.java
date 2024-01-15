package com.se300.ledger.controller;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(classes = TestSmartStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LedgerRestControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers;

    @BeforeAll
    static void init(){

        headers = new HttpHeaders();
        headers.setBasicAuth("sergey", "chapman");
    }

    @Test
    public void testGetAccountById() throws IllegalStateException, JSONException {

        String expectedJson = "{\"address\" : \"master\", \"balance\" : 2147483647}";

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/master", HttpMethod.GET, new HttpEntity<String>(headers),
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(),true);
    }

    @Test
    public void testPostAccount() throws IllegalStateException, JSONException {

        String expectedJson = "{\"address\" : \"sergey\", \"balance\" : 0}";
        Account account = new Account("sergey", 0);

        HttpEntity<Account> request = new HttpEntity<>(account, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/accounts", request, String.class);
        JSONAssert.assertEquals(expectedJson, response.getBody(),true);
    }

    @Test
    public void testGetTransactionById() throws IllegalStateException, JSONException {

        //TODO: Implement Transaction Retrieval Test Method
    }

    @Test
    public void testPostTransaction() throws IllegalStateException, JSONException{
        //TODO: Implement Transaction Processing Test Method
    }
}
