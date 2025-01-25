package com.github.roknikolic.springcrudapp;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class ProductController {
    private Map<String, Product> db = new HashMap<>() {{
        put("1", new Product("1", "name1", "blabla", 0.0F));
    }};

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Product api";
    }

    @GetMapping("/products")
    public Collection<Product> read() {
        return db.values();
    }

    @GetMapping("/product/{id}")
    public Product read(@PathVariable String id) {
        Product product = db.get(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return product;
        }
    }

    @DeleteMapping("/product/{id}")
    public void delete(@PathVariable String id) {
        Product product = db.remove(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public Product create(@RequestBody @Valid Product product) {
        System.out.println(product);
        product.setId(UUID.randomUUID().toString());
        db.put(product.getId(), product);
        return product;
    }

    @PutMapping("/product/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product) {
        System.out.println("Updating");
        //TODO
        return product;
    }
}
