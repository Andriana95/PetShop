package com.example.demo.service;

import com.example.demo.controller.ProductOrderData;
import com.example.demo.controller.ProductOrderDetails;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${stripe.pk}")
    private String stripePk;
    @Value("${stripe.sk}")
    private String stripeSk;

    @Override
    public Order placeOrder(ProductOrderData productOrderData) {
         Stripe.apiKey = stripeSk;
         List<Product> productsForOrder = new ArrayList();
        long priceSum = 0;
        for (ProductOrderDetails productOrderDetails : productOrderData.getProductData()) {
            Product product = productRepository.getOne(productOrderDetails.getProductId());
            productsForOrder.add(product);
            priceSum += (product.getPrice() * productOrderDetails.getAmount()) * 100;
        }

        try {
            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("amount", priceSum); // Amount in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", productOrderData.getToken());
            chargeParams.put("description", "Charge for product");

            Charge createdCharge = Charge.create(chargeParams);
            String chargeId = createdCharge.getId();
            Order order = new Order();
            order.setPrice(priceSum);
            order.setChargeId(chargeId);
            order.setProducts(productsForOrder);
            return orderRepository.save(order);
        } catch (CardException e) {
            // The card has been declined
        } catch (APIException | InvalidRequestException | APIConnectionException | AuthenticationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
