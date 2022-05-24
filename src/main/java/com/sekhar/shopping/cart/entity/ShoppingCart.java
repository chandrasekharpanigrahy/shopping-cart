package com.sekhar.shopping.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ShoppingCart {
    List<Item> item;
    private BigDecimal totalPrice;
}
