package com.gregzealley.namematcherapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GetController {

    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
    public String uploadPrimaryFile(@RequestParam(value = "primary_file") MultipartFile primaryFile,
                                    @RequestParam(value = "secondary_file") MultipartFile secondaryFile) {

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


