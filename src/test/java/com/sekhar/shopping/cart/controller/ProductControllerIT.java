package com.sekhar.shopping.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekhar.shopping.cart.entity.Product;
import com.sekhar.shopping.cart.repository.ProductRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql({"/schema.sql"})
public class ProductControllerIT {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_create_product_on_receiving_request() throws Exception {
        val request = post("/rest/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product(1L, "Soap", "Dove", BigDecimal.valueOf(39))));
        mockMvc.perform(request).andExpect(status().isCreated());
        Product product = repository.findById(1L).get();
        assertAll(
                () -> assertEquals(1, product.getId()),
                () -> assertEquals("Soap", product.getType())
        );
    }

}
