package com.gregzealley.namematcherapi.services;

import com.gregzealley.namematcherapi.enums.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NameMatcherService {

    private final String NEW_LINE_REGEX = "[\\r\\n]+";

    private String[] primaryFileContent;
    private String[] secondaryFileContent;

    public String initiateMatch(final MultipartFile primaryFile, final MultipartFile secondaryFile) {

        importFileIntoArray(primaryFile, FileType.PRIMARY);
        importFileIntoArray(secondaryFile, FileType.SECONDARY);

        return String.format("There are %s rows in the primary file and %s in the secondary file.",
                primaryFileContent.length, secondaryFileContent.length);
    }

    private void importFileIntoArray(MultipartFile file, FileType type) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String completeData = new String(bytes);

                if (type == FileType.PRIMARY)
                    primaryFileContent = completeData.split(NEW_LINE_REGEX);
                else
                    secondaryFileContent = completeData.split(NEW_LINE_REGEX);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
