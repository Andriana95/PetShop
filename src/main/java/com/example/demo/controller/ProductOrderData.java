package com.example.demo.controller;

import java.util.List;

public class ProductOrderData {
    private String token;
    private List<ProductOrderDetails> productData;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ProductOrderDetails> getProductData() {
        return productData;
    }

    public void setProductData(List<ProductOrderDetails> productData) {
        this.productData = productData;
    }
}
