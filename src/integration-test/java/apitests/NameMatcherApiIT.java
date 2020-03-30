package apitests;

import apitests.services.NameMatcherApiTestService;
import com.gregzealley.namematcherapi.NameMatcherApiApplication;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NameMatcherApiApplication.class)
public class NameMatcherApiIT {

    private NameMatcherApiTestService nameMatcherApiTestService;

    private final String NAME_OF_RESULT_FILE = "result.csv";

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        nameMatcherApiTestService = new NameMatcherApiTestService();
    }

    @After
    public void teardown() {
        File fileToDelete = new File(NAME_OF_RESULT_FILE);
        fileToDelete.delete();
    }

    @Test
    public void whenBothFilesAreEmptyThenNoMatches() {

        File primFile = readFileFromTestResources("match_type_one/test_one/prim_empty.csv");
        File secFile = readFileFromTestResources("match_type_one/test_one/sec_empty.csv");
        File expectedResult = readFileFromTestResources("match_type_one/test_one/result.csv");

        ExtractableResponse<Response> response = nameMatcherApiTestService.callUploadFiles(primFile, secFile);
        File actualResult = createResultFile(response.asByteArray());

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.SC_OK);

        Assert.assertEquals(expectedResult, actualResult);
    }

    private File readFileFromTestResources(final String filename) {

        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
    }

    private File createResultFile(byte[] bFile) {

        try (FileOutputStream fileOuputStream = new FileOutputStream(NAME_OF_RESULT_FILE)) {
            fileOuputStream.write(bFile);
            return new File(NAME_OF_RESULT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
