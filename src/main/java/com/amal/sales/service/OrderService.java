package com.amal.sales.service;

import com.amal.sales.Enum.OrderStatus;
import com.amal.sales.entity.Order;
import com.amal.sales.entity.Product;
import com.amal.sales.exception.InsufficientStockException;
import com.amal.sales.repository.OrderRepository;
import com.amal.sales.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found"));

        Product product = productRepository.findById(order.getProductId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,   "Product not found"));

        if(order.getOrderStatus() == OrderStatus.CONFIRMED){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order already confirmed");
        }

        if(product.getStock()<order.getQuantity()){
            throw new InsufficientStockException("Not enough stock");
        }

        product.setStock(product.getStock() - order.getQuantity());
        productRepository.save(product);

        order.setOrderStatus(OrderStatus.CONFIRMED);
         return orderRepository.save(order);

    }
}