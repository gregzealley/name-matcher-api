package apitests;

import com.gregzealley.namematcherapi.NameMatcherApiApplication;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NameMatcherApiApplication.class)
public class NameMatcherApiIT {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void whenPassTwoPopulatedFilesThenGetCorrectResponse() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file1 = new File(classLoader.getResource("api_test_files/populated_file_1.txt").getFile());
        File file2 = new File(classLoader.getResource("api_test_files/populated_file_2.txt").getFile());

        ExtractableResponse response = given()
                .multiPart("primary_file", file1)
                .multiPart("secondary_file", file2)
                .when()
                .post("uploadfiles")
                .then()
                .extract();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.SC_OK);

        assertThat(response.body().asString())
                .isEqualTo("There are 4 rows in the primary file and 3 in the secondary file.");
    }

}
