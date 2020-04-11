package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public Product createProduct(@RequestBody ProductData productData) {
        return productService.create(productData);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/recommended")
    public List<Product> getRecommendedProducts() {
        return productService.findAll().subList(0, 3);
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getOne(id);
    }

    @PostMapping("/products/order")
    public Charge placeOrders(@RequestBody ProductOrderData productOrderData) {
        Charge charges = productService.placeOrder(productOrderData);
        System.out.println(charges);
        return charges;
    }


}