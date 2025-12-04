package com.example.orderapp.service;

import com.example.orderapp.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>();
    private final Random random = new Random();

    public List<Order> getOrders() {
        return orders;
    }

    public Order createOrder() {
        Order order = new Order(random.nextInt(1000), "ORDER_CONFIRMED");
        orders.add(order);
        return order;
    }
}
