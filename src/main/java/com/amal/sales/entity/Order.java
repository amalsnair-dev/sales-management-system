package com.amal.sales.entity;

import com.amal.sales.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}