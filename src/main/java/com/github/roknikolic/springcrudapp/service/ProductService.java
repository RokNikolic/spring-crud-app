package com.github.roknikolic.springcrudapp.service;

import com.github.roknikolic.springcrudapp.model.Product;
import com.github.roknikolic.springcrudapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> readAll() {
        return productRepository.findAll();
    }

    public Product read(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        productRepository.deleteById(id);
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public Product update(Integer id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            product.setId(id);
            productRepository.save(product);
            return product;
        } else {
            return null;
        }
    }
}
