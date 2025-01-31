package com.github.roknikolic.springcrudapp.product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductController {
    // The Product controller class for the api end points

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Product api";
    }

    @GetMapping("/product")
    public Iterable<Product> read() {
        return productService.read();
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
    public Product delete(@PathVariable Integer id) {
        Product deletedProduct = productService.remove(id);
        if (deletedProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return deletedProduct;
        }
    }

    @PostMapping("/product")
    public Product create(@RequestBody @Valid Product product) {
        Product createdProduct = productService.create(product);
        if (createdProduct == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return createdProduct;
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
