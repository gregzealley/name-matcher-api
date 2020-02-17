package com.gregzealley.namematcherapi.controllers;

import com.gregzealley.namematcherapi.services.NameMatchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class GetController {

    private final NameMatchService nameMatchService;

    public GetController(final NameMatchService nameMatchService) {
        this.nameMatchService = nameMatchService;
    }

    @RequestMapping(value = "/singleFile", method = RequestMethod.POST)
    public String uploadSingleFile(@RequestParam(value = "file") MultipartFile file) {

        String completeData = "";

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                completeData = new String(bytes, UTF_8);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return completeData;
    }
}


