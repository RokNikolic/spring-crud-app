package com.github.roknikolic.springcrudapp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerIT {
    // Integration tests for the product controller AIP end points, using an in memory H2 db.

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void root() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to Product api")));
    }

    @Test
    public void readAllProducts() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"));
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n2\", \"description\": \"d2\", \"price\": \"2.1\"}"));

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void readProductById_Found() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"));

        mockMvc.perform(get("/product/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("n1"))
                .andExpect(jsonPath("$.description").value("d1"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("1.1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void readProductById_NotFound() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"));

        mockMvc.perform(get("/product/{1}", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteProductById() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"));

        mockMvc.perform(delete("/product/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("n1"))
                .andExpect(jsonPath("$.description").value("d1"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("1.1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void deleteProductByIdNotFound() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"));

        mockMvc.perform(delete("/product/{2}", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createProductById() throws Exception {
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("n1"))
                .andExpect(jsonPath("$.description").value("d1"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("1.1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateProductById() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n2\", \"description\": \"d2\", \"price\": \"2.1\"}"));

        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\", \"id\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("n1"))
                .andExpect(jsonPath("$.description").value("d1"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("1.1")))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void updateProductByIdNotFound() throws Exception {
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\"}"));

        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"n1\", \"description\": \"d1\", \"price\": \"1.1\", \"id\":\"2\"}"))
                .andExpect(status().isNotFound());
    }

}