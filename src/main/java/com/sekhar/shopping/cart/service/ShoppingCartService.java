package com.sekhar.shopping.cart.service;


import com.sekhar.shopping.cart.entity.Item;
import com.sekhar.shopping.cart.entity.ShoppingCart;
import com.sekhar.shopping.cart.repository.ProductRepository;
import com.sekhar.shopping.cart.repository.ItemRepository;
import lombok.val;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ShoppingCartService {
    private final ItemRepository repository;
    private final ProductRepository productRepository;

    public ShoppingCartService(ItemRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public void add(Item item) {
        Item itemFromDB = repository.findById(item.getProductCode())
                .map(it -> new Item(it.getProductCode(), it.getUserName(), it.getCount() + item.getCount()))
                .orElse(item);
        repository.save(itemFromDB);

    }
    public ShoppingCart get(String userName) {
        List<Item> allItems = repository.findAllByUserName(userName);
        BigDecimal totalPrice = allItems.stream().map(it -> {
            val price = productRepository.findById(it.getProductCode()).get().getPrice();
            return price.multiply(BigDecimal.valueOf(it.getCount()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ShoppingCart(allItems, totalPrice.setScale(2, RoundingMode.HALF_UP));
    }
}
