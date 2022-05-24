package com.sekhar.shopping.cart.repository;

import com.sekhar.shopping.cart.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
