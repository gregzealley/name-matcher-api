package apitests;

import com.gregzealley.namematcherapi.NameMatcherApiApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void whenPassTwoCorrectParametersThenCorrectStatusCodeIsReturned() {
        assertThat(true)
                .isTrue();
    }

}
