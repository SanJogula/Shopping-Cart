package com.scaler.productservice.service;

import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("localProductService")
public class LocalProductService implements ProductService {

    private ProductRepository productRepository;;
    private CategoryRepository categoryRepository;

    public LocalProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(productId);
        if(p.isPresent()) {
            return p.get();
        }
        throw new ProductNotFoundException("Product not found");
    }

    @Override
    public Product createProduct(Product product) {
        // Lets say we are not passing category ID in our request body
        Category cat = categoryRepository.findByTitle(product.getCategory().getTitle());
        if(cat == null) {
            // No category with our title in the database
            Category newCat = new Category();
            newCat.setTitle(product.getCategory().getTitle());
            Category newRow = categoryRepository.save(newCat);
            // newRow will have now catId
            product.setCategory(newRow);
        } else {
            product.setCategory(cat);
        }

        Product savedProduct = productRepository.save(product);
        return savedProduct;

    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException, CategoryNotFoundException {
        Optional<Product> p = productRepository.findById(product.getId());
        if (p.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        Product existingProductInDb = p.get();
        if (product.getTitle() != null) {
            existingProductInDb.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            existingProductInDb.setDescription(product.getDescription());
        }
        if (product.getPrice() != 0.0) {
            existingProductInDb.setPrice(product.getPrice());
        }
        if (product.getImageUrl() != null) {
            existingProductInDb.setImageUrl(product.getImageUrl());
        }
        if (product.getHeight() != 0) {
            existingProductInDb.setHeight(product.getHeight());
        }
        if (product.getWeight() != 0) {
            existingProductInDb.setWeight(product.getWeight());
        }
        if (product.getCategory() != null) {
            Category category = categoryRepository.findByTitle(product.getCategory().getTitle());
            if(category == null) {
                throw new CategoryNotFoundException("Product not found");
            } else {
                product.setCategory(category);
            }
            existingProductInDb.setCategory(product.getCategory());
        }
        return productRepository.save(existingProductInDb);
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(productId);
        if (p.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
        return p.get();
    }
}
