package com.sekhar.shopping.cart.service;

import com.sekhar.shopping.cart.entity.Item;
import com.sekhar.shopping.cart.entity.Product;
import com.sekhar.shopping.cart.entity.ShoppingCart;
import com.sekhar.shopping.cart.repository.ItemRepository;
import com.sekhar.shopping.cart.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartWithSalesTaxRateServiceTest {

    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ShoppingCartWithSalesTaxRateService service = new ShoppingCartWithSalesTaxRateService(itemRepository, productRepository);

    @Test
    public void should_call_product_repo_to_get_price_and_calculate_total_amount_equal_to_sum_of_all_items_price() {
        // Given
        when(itemRepository.findAllByUserName("chandra"))
                .thenReturn(List.of(new Item(1L, "chandra", 2),
                        new Item(2L, "chandra", 2)));
        when(productRepository.findById(1L))
                .thenReturn(of(new Product(1L, "Dove", "Soap", valueOf(39.99))));
        when(productRepository.findById(2L))
                .thenReturn(of(new Product(2L, "Axe", "Deo", valueOf(99.99))));

        // When
        ShoppingCart shoppingCart = service.get("chandra", valueOf(12.5));

        // Then
        verify(itemRepository).findAllByUserName("chandra");
        verify(productRepository).findById(1L);
        assertEquals(valueOf(314.96), shoppingCart.getTotalPrice());

    }
}