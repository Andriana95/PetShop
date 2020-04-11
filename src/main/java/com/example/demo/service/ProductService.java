package com.example.demo.service;

import com.example.demo.controller.ProductData;
import com.example.demo.controller.ProductOrderData;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product create(ProductData productData);

    List<Product> findAll();

    Product getOne(Long id);
}
