package com.gregzealley.namematcherapi;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NameMatcherApiIT {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void whenPassTwoCorrectParametersThenCorrectStatusCodeIsReturned() {
        when()
                .get("nameMatch?primaryList=aaa&secondaryList=ccc")
                .then()
                .statusCode(200);
    }

}
