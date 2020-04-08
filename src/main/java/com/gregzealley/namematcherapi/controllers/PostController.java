package com.gregzealley.namematcherapi.controllers;

import com.gregzealley.namematcherapi.models.MatchResult;
import com.gregzealley.namematcherapi.services.NameMatcherService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    NameMatcherService nameMatcherService;

    public PostController(final NameMatcherService nameMatcherService) {
        this.nameMatcherService = nameMatcherService;
    }

    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST, produces = "text/csv")
    public void uploadPrimaryFile(@RequestParam(value = "primary_file") MultipartFile primaryFile,
                                  @RequestParam(value = "secondary_file") MultipartFile secondaryFile,
                                  HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        List<MatchResult> results = nameMatcherService.coordinateNameMatching(primaryFile, secondaryFile);

        String filename = "match_results.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<MatchResult> writer = new StatefulBeanToCsvBuilder<MatchResult>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        writer.write(results);
    }
}


