package apitests.services;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class NameMatcherApiTestService {

    public ExtractableResponse<Response> callUploadFiles(final File primaryFile, final File secondaryFile) {

        return given()
                .multiPart("primary_file", primaryFile)
                .multiPart("secondary_file", secondaryFile)
                .when()
                .post("uploadfiles")
                .then()
                .extract();
    }

    public ExtractableResponse<Response> callUploadFiles(final String primaryFile, final String secondaryFile) {

        return given()
                .multiPart("primary_file", primaryFile)
                .multiPart("secondary_file", secondaryFile)
                .when()
                .post("uploadfiles")
                .then()
                .extract();
    }
}
