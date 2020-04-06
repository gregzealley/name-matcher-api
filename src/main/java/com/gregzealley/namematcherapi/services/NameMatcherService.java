package com.gregzealley.namematcherapi.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.gregzealley.namematcherapi.models.MatchResult;
import com.gregzealley.namematcherapi.models.Person;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NameMatcherService {

    private List<Person> primaryFileContent;
    private List<Person> secondaryFileContent;
    private List<MatchResult> matchResults;

    public NameMatcherService() {
        matchResults = new ArrayList<>();
    }

    public List<MatchResult> coordinateNameMatching(final MultipartFile primaryFile, final MultipartFile secondaryFile) throws IOException {

        importFiles(primaryFile, secondaryFile);
        matchNames();
        return matchResults;
    }

    private void importFiles(MultipartFile primaryFile, MultipartFile secondaryFile) throws IOException {
        primaryFileContent = importCsvFile(primaryFile);
        secondaryFileContent = importCsvFile(secondaryFile);
    }

    private void matchNames() {

        //Do nothing !
//        MatchResult example = new MatchResult();
//        example.firstName = "Bob";
//        example.lastName = "Smith";
//        example.matchResultType = MatchResultType.NO_MATCH;
//        matchResults.add(example);
    }

    private List<Person> importCsvFile(MultipartFile file) throws IOException {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Person.class);

        File csvFile = convertMultiPartToFile(file);
        MappingIterator<Person> personMappingIterator = mapper.readerFor(Person.class).with(schema).readValues(csvFile);

        deleteLocalFileAfterUse(csvFile);

        return personMappingIterator.readAll();
    }

    private void deleteLocalFileAfterUse(File file) {
        file.delete();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }
}
