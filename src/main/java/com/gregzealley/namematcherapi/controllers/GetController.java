package com.gregzealley.namematcherapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @GetMapping(value = "/nameMatch")
    public String nameMatch()
    {
        return "Hello World";
    }
}
