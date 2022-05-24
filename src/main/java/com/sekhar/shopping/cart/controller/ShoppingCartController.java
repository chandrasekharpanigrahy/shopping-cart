package com.sekhar.shopping.cart.controller;

import com.sekhar.shopping.cart.entity.ItemRequest;
import com.sekhar.shopping.cart.entity.ShoppingCart;
import com.sekhar.shopping.cart.service.ShoppingCartService;
import com.sekhar.shopping.cart.service.ShoppingCartWithSalesTaxRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(value = "/rest/v1/cart")
public class ShoppingCartController {

    private final ShoppingCartService service;

    private final ShoppingCartWithSalesTaxRateService serviceTaxRate;

    public ShoppingCartController(ShoppingCartService service, ShoppingCartWithSalesTaxRateService serviceWithTaxRate) {
        this.service = service;
        this.serviceTaxRate = serviceWithTaxRate;
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> addToCart(@RequestBody ItemRequest request){
        service.add(request.getItem());
        return status(CREATED).body(service.get(request.getItem().getUserName()));
    }

    @PostMapping(path = "/withTax")
    public ResponseEntity<ShoppingCart> addToCartWithTax(@RequestBody ItemRequest request){
        service.add(request.getItem());
        return status(CREATED).body(serviceTaxRate.get(request.getItem().getUserName(), request.getSalesTaxRate()));
    }
}
