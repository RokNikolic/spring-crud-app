package com.github.roknikolic.springcrudapp.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        Product savedProduct = productRepository.save(product);

        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getId() > 0);
    }

}