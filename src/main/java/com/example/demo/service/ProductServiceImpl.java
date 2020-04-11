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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private CategoryRepository categoryRepository;


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
}
