package com.gregzealley.namematcherapi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.when;

@SpringBootTest
public class NameMatcherApiIT {

    @Value("${local.server.port}")
    private int serverPort;

    @Test
    public void whenPassTwoCorrectParametersThenCorrectStatusCodeIsReturned() {
        when()
                .get("/nameMatch&primaryList=plist&secondaryList=sList")
                .then()
                .statusCode(200);
    }

}
