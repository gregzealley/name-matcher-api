package com.gregzealley.namematcherapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class NameMatcherService {

    private List<Person> primaryFileContent;
    private List<Person> secondaryFileContent;
    private List<MatchResult> matchResults;

    public String coordinateNameMatching(final MultipartFile primaryFile, final MultipartFile secondaryFile) throws IOException {

        importFiles(primaryFile, secondaryFile);
        matchNames();
        exportToFile();

        return String.format("There are %s rows in the primary file and %s in the secondary file.",
                primaryFileContent.size(), secondaryFileContent.size());
    }

    private void importFiles(MultipartFile primaryFile, MultipartFile secondaryFile) throws IOException {
        primaryFileContent = importCsvFile(primaryFile);
        secondaryFileContent = importCsvFile(secondaryFile);
    }

    private void matchNames() {
        matchResults = Collections.emptyList();
    }

    private void exportToFile() throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(MatchResult.class).withHeader();
        mapper.writer(schema).writeValueAsString(matchResults);
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
