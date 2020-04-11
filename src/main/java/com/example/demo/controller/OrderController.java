package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/products/order")
    public Order placeOrder(@RequestBody ProductOrderData productOrderData) {
        Order order = orderService.placeOrder(productOrderData);
        System.out.println(order);
        return order;
    }


}