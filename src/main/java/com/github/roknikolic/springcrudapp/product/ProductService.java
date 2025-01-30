package com.github.roknikolic.springcrudapp.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> read() {
        return productRepository.findAll();
    }

    public Product read(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product remove(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return optionalProduct.get();
        } else {
            return null;
        }
    }

    public Product create(Product product) {
        if ((product.getName() == null) ||  (product.getDescription() == null) || (product.getPrice() == null)) {
            return null;
        }
        product.setId(null); // We force auto generated IDs
        return productRepository.save(product);
    }

    public Product update(Product product) {
        if ((product.getName() == null) ||
            (product.getDescription() == null) ||
            (product.getPrice() == null) ||
            (product.getId() == null)) {
            return null;
        }
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isPresent()) {
            productRepository.save(product);
            return product;
        } else {
            return null;
        }
    }
}
