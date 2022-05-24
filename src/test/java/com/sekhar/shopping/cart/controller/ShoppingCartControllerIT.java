package com.sekhar.shopping.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekhar.shopping.cart.entity.Item;
import com.sekhar.shopping.cart.entity.ItemRequest;
import com.sekhar.shopping.cart.entity.Product;
import com.sekhar.shopping.cart.entity.ShoppingCart;
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
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql({"/schema.sql"})
public class ShoppingCartControllerIT {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void validate_shopping_cart_with_total_cart_price_without_tax() throws Exception {
        val productCreateRequest = post("/rest/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product(1L, "Soap", "Dove", BigDecimal.valueOf(39.99))));
        mockMvc.perform(productCreateRequest).andExpect(status().isCreated());

        val addToCartRequest = post("/rest/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new ItemRequest(new Item(1L, "chandra", 5), BigDecimal.ZERO)));
        val responseAsString = mockMvc.perform(addToCartRequest).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        val shoppingCart = mapper.readValue(responseAsString, ShoppingCart.class);
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(199.95), shoppingCart.getTotalPrice())
        );
    }

    @Test
    public void validate_addition_of_multiple_shopping_cart_with_total_cart_price_without_tax() throws Exception {
        val productCreateRequest = post("/rest/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product(1L, "Soap", "Dove", BigDecimal.valueOf(39.99))));
        mockMvc.perform(productCreateRequest).andExpect(status().isCreated());

        val addToCartRequestWithFiveDove = post("/rest/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new ItemRequest(new Item(1L, "chandra", 5), BigDecimal.ZERO)));
         mockMvc.perform(addToCartRequestWithFiveDove).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        val addToCartRequestWithEightDove = post("/rest/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new ItemRequest(new Item(1L, "chandra", 3), BigDecimal.ZERO)));
        val responseAsString = mockMvc.perform(addToCartRequestWithEightDove).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        val shoppingCart = mapper.readValue(responseAsString, ShoppingCart.class);
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(319.92), shoppingCart.getTotalPrice())
        );
    }

    @Test
    public void validate_addition_of_multiple_shopping_cart_with_total_cart_price_with_tax() throws Exception {
        val productDoveSoapCreateRequest = post("/rest/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product(1L, "Soap", "Dove", BigDecimal.valueOf(39.99))));
        mockMvc.perform(productDoveSoapCreateRequest).andExpect(status().isCreated());

        val productAxeDeoCreateRequest = post("/rest/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product(2L, "Deo", "Axe", BigDecimal.valueOf(99.99))));
        mockMvc.perform(productAxeDeoCreateRequest).andExpect(status().isCreated());

        val addToCartRequestWithFiveDove = post("/rest/v1/cart/withTax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new ItemRequest(new Item(1L, "chandra", 2), BigDecimal.valueOf(12.5))));
        mockMvc.perform(addToCartRequestWithFiveDove).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        val addToCartRequestWithEightDove = post("/rest/v1/cart/withTax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new ItemRequest(new Item(2L, "chandra", 2), BigDecimal.valueOf(12.5))));
        val responseAsString = mockMvc.perform(addToCartRequestWithEightDove).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        val shoppingCart = mapper.readValue(responseAsString, ShoppingCart.class);
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(314.96), shoppingCart.getTotalPrice())
        );
    }
}
