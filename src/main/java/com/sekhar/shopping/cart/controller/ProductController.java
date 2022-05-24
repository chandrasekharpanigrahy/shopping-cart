package com.sekhar.shopping.cart.controller;

import com.sekhar.shopping.cart.service.ProductService;
import com.sekhar.shopping.cart.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rest/v1/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> addProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(product));
    }

}
