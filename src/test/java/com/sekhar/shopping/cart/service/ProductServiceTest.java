package com.sekhar.shopping.cart.service;


import com.sekhar.shopping.cart.entity.Product;
import com.sekhar.shopping.cart.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    private ProductRepository repository = mock(ProductRepository.class);
    private ProductService service = new ProductService(repository);

    @Test
    public void verify_call_to_repo_for_service_and_return_id(){
        when(repository.save(any())).thenReturn(new Product(1L, "Dove", "Soap", BigDecimal.valueOf(39)));
        Long id = service.add(new Product());
        verify(repository).save(any());
        assertEquals(id,1);
    }

}