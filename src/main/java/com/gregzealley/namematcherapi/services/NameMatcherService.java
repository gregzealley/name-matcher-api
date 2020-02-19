package com.gregzealley.namematcherapi.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NameMatcherService {

    public String initiateMatch(final MultipartFile primaryFile, final MultipartFile secondaryFile) {

        String[] primaryFileContent = new String[0];
        String[] secondaryFileContent = new String[0];

        primaryFileContent = convertToArray(primaryFile, primaryFileContent);
        secondaryFileContent = convertToArray(secondaryFile, secondaryFileContent);

        return String.format("There are %s rows in the primary file and %s in the secondary file.",
                primaryFileContent.length, secondaryFileContent.length);
    }

    private String[] convertToArray(MultipartFile primaryFile, String[] primaryFileContent) {
        if (!primaryFile.isEmpty()) {
            try {
                byte[] bytes = primaryFile.getBytes();
                String completeData = new String(bytes);

                primaryFileContent = completeData.split("[\\r\\n]+");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return primaryFileContent;
    }
}
