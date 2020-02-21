package apitests;

import apitests.services.NameMatcherApiTestService;
import com.gregzealley.namematcherapi.NameMatcherApiApplication;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NameMatcherApiApplication.class)
public class NameMatcherApiIT {

    private NameMatcherApiTestService nameMatcherApiTestService;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        nameMatcherApiTestService = new NameMatcherApiTestService();
    }

    @Test
    public void whenPostTwoPopulatedTxtFilesThenGetOkStatusAndCorrectMessage() {

        File file1 = readFileFromTestResources("api_test_files/txt_file_populated_1.txt");
        File file2 = readFileFromTestResources("api_test_files/txt_file_populated_2.txt");

        ExtractableResponse<Response> response = nameMatcherApiTestService.callUploadFiles(file1, file2);

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.SC_OK);

        assertThat(response.body().asString())
                .isEqualTo("There are 4 rows in the primary file and 3 in the secondary file.");
    }

    @Test
    public void whenPostTwoEmptyTxtFilesThenGetOkStatusAndCorrectMessage() {

        File file1 = readFileFromTestResources("api_test_files/txt_file_empty_1.txt");
        File file2 = readFileFromTestResources("api_test_files/txt_file_empty_2.txt");

        ExtractableResponse<Response> response = nameMatcherApiTestService.callUploadFiles(file1, file2);

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.SC_OK);

        assertThat(response.body().asString())
                .isEqualTo("There are 0 rows in the primary file and 0 in the secondary file.");
    }

    @Test
    public void whenPostTwoCsvPopulatedFilesThenGetOkStatusAndMessage() {}

    @Test
    public void whenPostNoFilesThenGetMalformedRequestStatus() {}

    @Test
    public void whenPostOnlyPrimaryFileThenGetMalformedRequestStatus() {}

    @Test
    public void whenPostOnlySecondaryFileThenGetMalformedRequestStatus() {}

    @Test
    public void whenPostOneNonTextFileThenGetGetMalformedRequestStatus() {}

    private File readFileFromTestResources(final String filename) {

        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
    }
}
