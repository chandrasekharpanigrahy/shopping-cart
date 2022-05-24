package com.sekhar.shopping.cart.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Product {
    @Id
    private Long id;
    private String type;
    private String companyName;
    private BigDecimal price;
}
