package com.gregzealley.namematcherapi.controllers;

import com.gregzealley.namematcherapi.services.NameMatchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/singleFile", headers = "content-type=multipart/*")
    //@RequestMapping(value = "/singleFile", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces = {"text/plain"})
    public String uploadSingleFile(@RequestParam(value = "file") MultipartFile file) throws IOException {

//        String completeData = "";
//
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                completeData = new String(bytes);
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//            }
//        }
//        return completeData;
        return "It works!";
    }


//    // This works but only grabs the filepath, not the content... plan b
//    @RequestMapping(value = "/singleFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public String getAcquisition(@RequestParam(value = "file", required = true) String filePath) throws FileNotFoundException {
//
//        return filePath;
//
//    }
}


