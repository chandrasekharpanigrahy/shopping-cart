package com.sekhar.shopping.cart.service;


import com.sekhar.shopping.cart.entity.Product;
import com.sekhar.shopping.cart.repository.ProductRepository;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Long add(Product product) {
        return repository.save(product).getId();
    }
}
