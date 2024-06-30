package com.santhosh.productservice.service;

import com.santhosh.productservice.dto.FakeStoreProductDto;
import com.santhosh.productservice.exceptions.ProductNotFoundException;
import com.santhosh.productservice.model.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        for (FakeStoreProductDto fs : response) {
            products.add(fs.toProduct());
        }
        return products;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product not found " +
                    "with id " + productId);
        }
        return fakeStoreProductDto.toProduct();
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fs = makeProductFromFakeStoreProduct(product);
        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fs,
                FakeStoreProductDto.class
        );
        return response.toProduct();
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        FakeStoreProductDto fs = makeProductFromFakeStoreProduct(productDetails);
        //FakeStoreProductDto updatedProduct = restTemplate.patchForObject("https://fakestoreapi.com/products/" + id,
        //        fs, FakeStoreProductDto.class);
        //return updatedProduct.toProduct();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fs, headers);
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreProductDto.class
        );
        FakeStoreProductDto updatedProduct = responseEntity.getBody();
        return updatedProduct.toProduct();
    }

    @Override
    public Product deleteProduct(Long productId) {
        //restTemplate.delete("https://fakestoreapi.com/products/" + productId);
        //FakeStoreProductDto deletedProduct = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId,
        //        FakeStoreProductDto.class
        //);

        // fakestoreapi.com return deleted product details, so to get the deleted product details, we can use exchange method

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.DELETE,
                requestEntity,
                FakeStoreProductDto.class
        );
        FakeStoreProductDto deletedProduct = responseEntity.getBody();
        return deletedProduct.toProduct();
    }

    private FakeStoreProductDto makeProductFromFakeStoreProduct(Product product) {
        FakeStoreProductDto fs = new FakeStoreProductDto();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setCategory(product.getCategory().getTitle());
        fs.setImage(product.getImageUrl());
        fs.setDescription(product.getDescription());
        fs.setPrice(product.getPrice());
        return fs;
    }
}