package com.amal.sales.controller;

import com.amal.sales.entity.Order;
import com.amal.sales.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/confirm/{id}")
    public Order confirm(@PathVariable Long id){
        return orderService.confirmOrder(id);
    }
}