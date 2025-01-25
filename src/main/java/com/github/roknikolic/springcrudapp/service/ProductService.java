package com.github.roknikolic.springcrudapp.service;

import com.github.roknikolic.springcrudapp.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private Map<String, Product> db = new HashMap<>() {{
        put("1", new Product("1", "name1", "desc", 0.0F));
    }};

    public Collection<Product> readAll() {
        return db.values();
    }

    public Product read(String id) {
        return db.get(id);
    }

    public Product remove(String id) {
        return db.remove(id);
    }

    public Product create(String id, Product product) {
        db.put(id, product);
        return product;
    }

    public Product update(String id, Product product) {
        db.put(id, product);
        return product;
    }
}
