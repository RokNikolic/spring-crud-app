package com.github.roknikolic.springcrudapp.product;

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

    public Product create(Product product) {
        product.setId(null);
        return productRepository.save(product);
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
