package com.github.roknikolic.springcrudapp.product;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE)
class ProductServiceMT {
    // Mock tests for the product service.

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @AfterEach
    public void tearDown() {
        Mockito.reset(productRepository);
    }

    @Test
    public void readProductByIdFound() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"), 1);

        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.read(1);
        assertNotNull(foundProduct);
        assertEquals("name1", foundProduct.getName());
    }

    @Test
    public void readProductByIdNotFound() {
        Mockito.when(productRepository.findById(3)).thenReturn(Optional.empty());

        Product product = productService.read(3);
        assertNull(product);
    }

    @Test
    public void saveProductNoID() {
        Product product = new Product("name1", "des1", new BigDecimal("1.1"));

        Product mockProduct = new Product("name1", "des1", new BigDecimal("1.1"), 1);
        Mockito.when(productRepository.save(product)).thenReturn(mockProduct);

        Product savedProduct = productService.create(product);

        Assertions.assertNotNull(savedProduct);
        assertEquals(1, (int) savedProduct.getId());
    }

    @Test
    public void saveProductWithID() {
        Product product = new Product("name1", "description1", new BigDecimal("1.1"), 3);

        Product mockProduct = new Product("name1", "des1", new BigDecimal("1.1"), 1);
        Mockito.when(productRepository.save(product)).thenReturn(mockProduct);

        Product savedProduct = productService.create(product);

        Assertions.assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getId());
    }

    @Test
    public void removeProductById_Found() {
        Product mockProduct = new Product("name1", "des1", new BigDecimal("1.1"), 1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));

        Product deletedProduct = productService.remove(1);
        assertNotNull(deletedProduct);
        assertEquals("name1", deletedProduct.getName());
    }

    @Test
    public void removeProductById_NotFound() {
        Mockito.when(productRepository.findById(2)).thenReturn(Optional.empty());

        Product deletedProduct = productService.remove(2);
        assertNull(deletedProduct);
    }

    @Test
    public void updateProductById_Found() {
        Product mockProduct = new Product("name1", "des1", new BigDecimal("1.1"), 1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        Product newMockProduct = new Product("newName", "desc", new BigDecimal("3.7"), 1);
        Mockito.when(productRepository.save(newMockProduct)).thenReturn(newMockProduct);

        Product updatedProduct = productService.update(newMockProduct);
        assertNotNull(updatedProduct);
        assertEquals("newName", updatedProduct.getName());
    }

    @Test
    public void updateProductById_NotFound() {
        Product newMockProduct = new Product("newName", "desc", new BigDecimal("3.7"), 2);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());

        Product updatedProduct = productService.update(newMockProduct);
        assertNull(updatedProduct);
    }
}