package com.gregzealley.namematcherapi.controllers;

import com.gregzealley.namematcherapi.services.NameMatcherService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class PostController {

    NameMatcherService nameMatcherService;

    public PostController(final NameMatcherService nameMatcherService) {
        this.nameMatcherService = nameMatcherService;
    }

    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST, produces = "text/csv")
    public ResponseEntity uploadPrimaryFile(@RequestParam(value = "primary_file") MultipartFile primaryFile,
                                            @RequestParam(value = "secondary_file") MultipartFile secondaryFile) throws IOException {

        File result = nameMatcherService.coordinateNameMatching(primaryFile, secondaryFile);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + "result.csv")
                .contentLength(result.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(result));
    }
}


