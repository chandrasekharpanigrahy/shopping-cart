package com.sekhar.shopping.cart.repository;

import com.sekhar.shopping.cart.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    public List<Item> findAllByUserName(String userName);
}
