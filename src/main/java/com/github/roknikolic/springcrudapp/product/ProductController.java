package com.github.roknikolic.springcrudapp.product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Product create(@RequestBody @Valid Product product) {
        Product createdProduct = productService.create(product);
        if (createdProduct == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return product;
        }
    }

    @PutMapping("/product")
    public Product update(@RequestBody @Valid Product product) {
        Product updatedProduct = productService.update(product);
        if (updatedProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return updatedProduct;
        }
    }
}
