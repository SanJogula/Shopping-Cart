package com.scaler.productservice.service;

import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.model.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails) throws ProductNotFoundException, CategoryNotFoundException;
    Product deleteProduct(Long productId) throws ProductNotFoundException;
}
