package com.github.roknikolic.springcrudapp.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    // The Product repository interface for the communication with the database
}
