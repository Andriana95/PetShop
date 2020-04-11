package com.example.demo.service;

import com.example.demo.controller.ProductData;
import com.example.demo.controller.ProductOrderData;
import com.example.demo.controller.ProductOrderDetails;
import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ManufacturerRepository;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${stripe.pk}")
    String stripePk;
    @Value("${stripe.sk}")
    String stripeSk;

    @Override
    public Product create(ProductData productData) {
        Product product = new Product();
        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setAge(productData.getAge());
        product.setWeight(productData.getWeight());
        product.setPrice(productData.getPrice());
        product.setImg(productData.getImg());

        if (productData.getManufacturerName() != null) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(productData.getManufacturerName());
            manufacturer = this.manufacturerRepository.save(manufacturer);
            product.setManufacturer(manufacturer);
        }

        if (productData.getCategoryName() != null) {
            Category category = new Category();
            category.setName(productData.getCategoryName());
            category = this.categoryRepository.save(category);
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    public Charge placeOrder(ProductOrderData productOrderData) {
        Stripe.apiKey = stripeSk;

        float priceSum = 0;
        for (ProductOrderDetails productOrderDetails : productOrderData.getProductData()) {
            Product product = productRepository.getOne(productOrderDetails.getProductId());
            priceSum += (product.getPrice() * 100) * productOrderDetails.getAmount();
        }

        try {
            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("amount", (int) priceSum); // Amount in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", productOrderData.getToken());
            chargeParams.put("description", "Charge for product");

            return Charge.create(chargeParams);
        } catch (CardException e) {
            // The card has been declined
        } catch (APIException | InvalidRequestException | APIConnectionException | AuthenticationException e) {
            e.printStackTrace();
        }

        return null;
    }

}
