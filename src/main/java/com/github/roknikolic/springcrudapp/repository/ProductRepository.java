package com.github.roknikolic.springcrudapp.repository;

import com.github.roknikolic.springcrudapp.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
