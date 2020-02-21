package com.gregzealley.namematcherapi.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NameMatcherService {

    private final String NEW_LINE_REGEX = "[\\r\\n]+";

    private String[] primaryFileContent;
    private String[] secondaryFileContent;

    public String initiateMatch(final MultipartFile primaryFile, final MultipartFile secondaryFile) {

        primaryFileContent = importFileIntoArray(primaryFile);
        secondaryFileContent = importFileIntoArray(secondaryFile);

        return String.format("There are %s rows in the primary file and %s in the secondary file.",
                primaryFileContent.length, secondaryFileContent.length);
    }

    private String[] importFileIntoArray(MultipartFile file) {

        String[] fileContent = new String[0];

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String completeData = new String(bytes);

                fileContent = completeData.split(NEW_LINE_REGEX);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return fileContent;
    }
}
