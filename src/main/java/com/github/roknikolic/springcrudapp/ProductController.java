package com.github.roknikolic.springcrudapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }
}
