package com.sekhar.shopping.cart.service;


import com.sekhar.shopping.cart.entity.Item;
import com.sekhar.shopping.cart.entity.ShoppingCart;
import com.sekhar.shopping.cart.repository.ItemRepository;
import com.sekhar.shopping.cart.repository.ProductRepository;
import lombok.val;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.*;

public class ShoppingCartWithSalesTaxRateService {
    private final ItemRepository repository;
    private final ProductRepository productRepository;

    public ShoppingCartWithSalesTaxRateService(ItemRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public void add(Item item) {
        Item itemFromDB = repository.findById(item.getProductCode())
                .map(it -> new Item(it.getProductCode(), it.getUserName(), it.getCount() + item.getCount()))
                .orElse(item);
        repository.save(itemFromDB);
    }

    public ShoppingCart get(String userName, BigDecimal salesTaxRate) {
        List<Item> allItems = repository.findAllByUserName(userName);
        BigDecimal totalPrice = allItems.stream().map(it -> {
            val price = productRepository.findById(it.getProductCode()).get().getPrice();
            return price.multiply(valueOf(it.getCount()));
        }).reduce(ZERO, BigDecimal::add);
        BigDecimal totalPriceIncludingSalesTax = totalPrice.add(totalPrice.multiply(salesTaxRate).divide(valueOf(100)));
        return new ShoppingCart(allItems, totalPriceIncludingSalesTax.setScale(2, RoundingMode.HALF_UP));
    }
}
