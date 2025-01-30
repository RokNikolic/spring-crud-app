package com.github.roknikolic.springcrudapp.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllTest() {
        Product product1 = new Product("name1", "description1", new BigDecimal("1.1"));
        Product product2 = new Product("name2", "description2", new BigDecimal("2.1"));
        productRepository.save(product1);
        productRepository.save(product2);

        Iterable<Product> foundProducts = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        foundProducts.forEach(productList::add);

        assertEquals(2, productList.size());
    }
    @Test
    public void findByIdTest_Present() {
        Product product1 = new Product("name1", "description1", new BigDecimal("1.1"));
        productRepository.save(product1);

        Optional<Product> optionalProduct = productRepository.findById(1);
        assertTrue(optionalProduct.isPresent());
    }
    @Test
    public void findByIdTest_NotPresent() {
        Product product1 = new Product("name1", "description1", new BigDecimal("1.1"));
        productRepository.save(product1);

        Optional<Product> optionalProduct = productRepository.findById(2);
        assertFalse(optionalProduct.isPresent());
    }
    @Test
    public void saveTest() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        Product savedProduct = productRepository.save(product);

        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getId() > 0);
    }
    @Test
    public void deleteByIdTest() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productRepository.save(product);
        productRepository.deleteById(1);
        Optional<Product> optionalProduct = productRepository.findById(1);
        assertFalse(optionalProduct.isPresent());
    }
}