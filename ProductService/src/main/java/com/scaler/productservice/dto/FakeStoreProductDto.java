package com.scaler.productservice.dto;

import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import lombok.Data;

@Data
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public Product toProduct() {
        Product p = new Product();
        p.setId(id);
        p.setTitle(title);
        p.setPrice(price);
        p.setDescription(description);
        p.setImageUrl(image);

        Category cat = new Category();
        cat.setTitle(category);
        p.setCategory(cat);
        return p;
    }

    @Override
    public String toString() {
        return "FakeStoreProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}