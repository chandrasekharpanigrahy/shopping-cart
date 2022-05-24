package com.sekhar.shopping.cart.config;

import com.sekhar.shopping.cart.repository.ItemRepository;
import com.sekhar.shopping.cart.service.ProductService;
import com.sekhar.shopping.cart.repository.ProductRepository;
import com.sekhar.shopping.cart.service.ShoppingCartService;
import com.sekhar.shopping.cart.service.ShoppingCartWithSalesTaxRateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    ProductService productService(ProductRepository repository){
        return new ProductService(repository);
    }

    @Bean
    ShoppingCartService shoppingCartService(ItemRepository repository, ProductRepository productRepository){
        return new ShoppingCartService(repository, productRepository);
    }

    @Bean
    ShoppingCartWithSalesTaxRateService serviceWithTaxRate(ItemRepository repository, ProductRepository productRepository){
        return new ShoppingCartWithSalesTaxRateService(repository, productRepository);
    }

}
