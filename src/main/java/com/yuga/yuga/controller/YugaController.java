package com.yuga.yuga.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/yuga")
public class YugaController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
