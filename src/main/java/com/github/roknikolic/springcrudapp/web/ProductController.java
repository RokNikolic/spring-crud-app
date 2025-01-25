package com.github.roknikolic.springcrudapp.web;

import com.github.roknikolic.springcrudapp.model.Product;
import com.github.roknikolic.springcrudapp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Product api";
    }

    @GetMapping("/products")
    public Collection<Product> read() {
        return productService.readAll();
    }

    @GetMapping("/product/{id}")
    public Product read(@PathVariable String id) {
        Product product = productService.read(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return product;
        }
    }

    @DeleteMapping("/product/{id}")
    public void delete(@PathVariable String id) {
        Product product = productService.remove(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public Product create(@RequestBody @Valid Product product) {
        product.setId(UUID.randomUUID().toString());
        return productService.create(product.getId(), product);
    }

    @PutMapping("/product/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product) {
        return productService.update(id, product);
    }
}
