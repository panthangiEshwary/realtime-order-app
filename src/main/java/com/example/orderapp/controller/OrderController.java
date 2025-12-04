package com.example.orderapp.controller;

import com.example.orderapp.model.Order;
import com.example.orderapp.service.OrderService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final MeterRegistry registry;

    public OrderController(OrderService orderService, MeterRegistry registry) {
        this.orderService = orderService;
        this.registry = registry;
    }

    @GetMapping
    public List<Order> getOrders() {
        registry.counter("orders_api_hits").increment();
        return orderService.getOrders();
    }

    @PostMapping("/create")
    public Order createOrder() {
        registry.counter("orders_created").increment();
        return orderService.createOrder();
    }

    @GetMapping("/error")
    public String generateError() {
        registry.counter("orders_error_rate").increment();
        throw new RuntimeException("Simulated API Failure");
    }
}
