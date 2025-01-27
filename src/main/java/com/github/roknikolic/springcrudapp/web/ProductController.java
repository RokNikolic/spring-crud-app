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
    public Iterable<Product> readAll() {
        return productService.readAll();
    }

    @GetMapping("/product/{id}")
    public Product read(@PathVariable Integer id) {
        Product product = productService.read(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return product;
        }
    }

    @DeleteMapping("/product/{id}")
    public void delete(@PathVariable Integer id) {
        productService.remove(id);
    }

    @PostMapping("/product")
    public void create(@RequestBody @Valid Product product) {
        productService.create(product);
    }

    @PutMapping("/product/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product) {
        Product updatedProduct = productService.update(id, product);
        if (updatedProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return updatedProduct;
        }
    }
}
