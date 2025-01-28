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
        product.setId(null);
        productRepository.save(product);
    }

    public Product update(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isPresent()) {
            productRepository.save(product);
            return product;
        } else {
            return null;
        }
    }
}
