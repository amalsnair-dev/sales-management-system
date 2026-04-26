package com.amal.sales.service;

import com.amal.sales.Enum.OrderStatus;
import com.amal.sales.entity.Order;
import com.amal.sales.entity.Product;
import com.amal.sales.repository.OrderRepository;
import com.amal.sales.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(Order order) {
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Transactional
    public Order confirmOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        Product product = productRepository.findById(order.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        if(product.getStock()<order.getQuantity()){
            throw new  RuntimeException("Not enough stock");
        }

        product.setStock(product.getStock() - order.getQuantity());
        productRepository.save(product);

        order.setOrderStatus(OrderStatus.CONFIRMED);
         return orderRepository.save(order);

    }
}