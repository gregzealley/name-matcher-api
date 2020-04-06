package apitests;

import apitests.services.NameMatcherApiTestService;
import com.gregzealley.namematcherapi.NameMatcherApiApplication;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NameMatcherApiApplication.class)
public class NameMatcherApiIT {

    private NameMatcherApiTestService nameMatcherApiTestService;

    private final String NAME_OF_GENERATED_RESULT_FILE = "result.csv";

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        nameMatcherApiTestService = new NameMatcherApiTestService();
    }

    @After
    public void teardown() throws IOException {
        Files.delete(Paths.get(NAME_OF_GENERATED_RESULT_FILE));
    }

    @Test
    public void whenBothFilesAreEmptyThenNoMatches() throws IOException {

        File primFile = readFileFromTestResources("match_type_one/test_one/prim_empty.csv");
        File secFile = readFileFromTestResources("match_type_one/test_one/sec_empty.csv");
        File expectedResult = readFileFromTestResources("match_type_one/test_one/result.csv");

        ExtractableResponse<Response> response = nameMatcherApiTestService.callUploadFiles(primFile, secFile);
        File actualResult = createReturnedFile(response.asByteArray());
        List<String> generatedActualCsvResult = Files.readAllLines(Paths.get(String.valueOf(actualResult)));

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.SC_OK);

        List<String> expectedCsvResult = Files.readAllLines(Paths.get(String.valueOf(expectedResult)));

        assertThat(expectedCsvResult)
                .isEqualTo(generatedActualCsvResult);
    }

    private File readFileFromTestResources(final String filename) {

        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
    }

    private File createReturnedFile(byte[] bFile) {

        try (FileOutputStream fileOuputStream = new FileOutputStream(NAME_OF_GENERATED_RESULT_FILE)) {
            fileOuputStream.write(bFile);
            return new File(NAME_OF_GENERATED_RESULT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
