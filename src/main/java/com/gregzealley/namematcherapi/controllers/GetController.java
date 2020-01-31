package com.gregzealley.namematcherapi.controllers;

import com.gregzealley.namematcherapi.services.NameMatchService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GetController {

    private final NameMatchService nameMatchService;

    public GetController(final NameMatchService nameMatchService) {
        this.nameMatchService = nameMatchService;
    }

//    @GetMapping(value = "/nameMatch")
//    public String nameMatch(@RequestParam(value = "primaryList") String primaryList,
//                            @RequestParam(value = "secondaryList") String secondaryList) {
//        return "Primary List: " + primaryList + " Secondary List: " + secondaryList;
//    }

    @RequestMapping(value = "/singleFile", consumes = "text/plain", method = RequestMethod.POST)
    public String uploadSingleFile(@RequestParam("file") MultipartFile file) throws IOException {

        String completeData = "";

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                completeData = new String(bytes);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return completeData;
    }
}


