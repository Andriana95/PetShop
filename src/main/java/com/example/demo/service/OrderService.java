package com.example.demo.service;

import com.example.demo.controller.ProductOrderData;
import com.example.demo.model.Order;

public interface OrderService {
    Order placeOrder(ProductOrderData productOrderData);
}
