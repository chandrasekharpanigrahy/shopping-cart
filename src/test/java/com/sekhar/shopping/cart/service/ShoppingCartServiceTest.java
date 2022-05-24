package com.sekhar.shopping.cart.service;

import com.sekhar.shopping.cart.entity.Item;
import com.sekhar.shopping.cart.entity.Product;
import com.sekhar.shopping.cart.entity.ShoppingCart;
import com.sekhar.shopping.cart.repository.ProductRepository;
import com.sekhar.shopping.cart.repository.ItemRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*Given:

        An empty shopping cart
        And a product, Dove Soap with a unit price of 39.99
        When:

        The user adds 5 Dove Soaps to the shopping cart
        Then:

        The shopping cart should contain 5 Dove Soaps each with a unit price of 39.99
        And the shopping cart’s total price should equal 199.95*/
class ShoppingCartServiceTest {

    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ShoppingCartService service = new ShoppingCartService(itemRepository, productRepository);

    @Test
    public void verify_call_to_repo_for_service_to_get_item_and_add_new_item() {
        when(itemRepository.save(any())).thenReturn(new Item(1L, "chandra", 2));
        when(itemRepository.findById(1L)).thenReturn(of(new Item(1L, "chandra", 1)));
        service.add(new Item());
        verify(itemRepository).save(any());
        verify(itemRepository).findById(any());
    }

    @Test
    public void should_call_product_repo_to_get_price_and_calculate_total_amount_equal_to_sum_of_all_items_price() {
        // Given
        when(productRepository.findById(1L))
                .thenReturn(of(new Product(1L, "Dove", "Soap", valueOf(39.99))));

        // When
        when(itemRepository.findAllByUserName("chandra"))
                .thenReturn(List.of(new Item(1L, "chandra", 5)));
        ShoppingCart shoppingCart = service.get("chandra");

        // Then
        verify(itemRepository).findAllByUserName("chandra");
        verify(productRepository).findById(1L);
        Item item = shoppingCart.getItem().get(0);
        assertEquals(item.getCount(), 5);
        assertEquals(item.getProductCode(), 1);
        assertEquals(shoppingCart.getTotalPrice(), valueOf(199.95));

    }
}