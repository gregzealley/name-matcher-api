package com.gregzealley.namematcherapi.controllers;

import com.gregzealley.namematcherapi.services.NameMatcherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PostController {

    NameMatcherService nameMatcherService;

    public PostController(final NameMatcherService nameMatcherService) {
        this.nameMatcherService = nameMatcherService;
    }

    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
    public String uploadPrimaryFile(@RequestParam(value = "primary_file") MultipartFile primaryFile,
                                    @RequestParam(value = "secondary_file") MultipartFile secondaryFile) throws IOException {

        return nameMatcherService.coordinateNameMatching(primaryFile, secondaryFile);
    }
}


