package com.gregzealley.namematcherapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @GetMapping(value = "/nameMatch")
    public String nameMatch(@RequestParam(value = "primaryList") String primaryList,
                            @RequestParam(value = "secondaryList") String secondaryList)
    {
        return "Primary List: " + primaryList + " Secondary List: " + secondaryList;
    }
}
