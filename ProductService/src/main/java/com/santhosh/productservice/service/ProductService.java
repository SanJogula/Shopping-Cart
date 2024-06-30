package com.santhosh.productservice.service;

import com.santhosh.productservice.exceptions.CategoryNotFoundException;
import com.santhosh.productservice.exceptions.ProductNotFoundException;
import com.santhosh.productservice.model.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails) throws ProductNotFoundException, CategoryNotFoundException;
    Product deleteProduct(Long productId) throws ProductNotFoundException;
}
