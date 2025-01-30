package com.github.roknikolic.springcrudapp.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void readAllProducts() {
        Product product1 = new Product("name1", "description1", new BigDecimal("1.1"));
        Product product2 = new Product("name2", "description2", new BigDecimal("2.1"));
        productService.create(product1);
        productService.create(product2);

        Iterable<Product> products = productService.read();
        List<Product> productList = new ArrayList<>();
        products.forEach(productList::add);
        assertEquals(2, productList.size());
    }

    @Test
    public void readProductByIdFound() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product);

        Product foundProduct = productService.read(1);
        assertNotNull(product);
        assertEquals("name1", foundProduct.getName());
    }
    @Test
    public void readProductByIdNotFound() {
        Product product1 = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product1);

        Product product = productService.read(3);
        assertNull(product);
    }
    @Test
    public void saveProductNoID() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        Product savedProduct = productService.create(product);

        Assertions.assertNotNull(savedProduct);
        assertEquals(1, (int) savedProduct.getId());
    }
    @Test
    public void saveProductWithID() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"), 3);
        Product savedProduct = productService.create(product);

        Assertions.assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getId());
    }
    @Test
    public void saveEmptyProduct() {
        Product product = new Product();
        Product savedProduct = productService.create(product);

        Assertions.assertNull(savedProduct);
    }
    @Test
    public void removeProductById_Found() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product);

        Product deletedProduct = productService.remove(1);
        assertNotNull(deletedProduct);
        assertEquals("name1", deletedProduct.getName());
    }
    @Test
    public void removeProductById_NotFound() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product);

        Product deletedProduct = productService.remove(2);
        assertNull(deletedProduct);
    }
    @Test
    public void updateProductById_Found() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product);

        Product newProduct = new Product("newName", "desc", new BigDecimal("3.7"), 1);

        Product updatedProduct = productService.update(newProduct);
        assertNotNull(updatedProduct);
        assertEquals("newName", updatedProduct.getName());
    }
    @Test
    public void updateProductById_NotFound() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product);

        Product newProduct = new Product("newName", "desc", new BigDecimal("3.7"), 2);

        Product updatedProduct = productService.update(newProduct);
        assertNull(updatedProduct);
    }
    @Test
    public void updateWithEmptyProduct() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"));
        productService.create(product);

        Product newProduct = new Product();

        Product updatedProduct = productService.update(newProduct);
        assertNull(updatedProduct);
    }


}