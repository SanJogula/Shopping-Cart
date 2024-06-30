# Shopping-Cart
E-Commerce Backend Application

This application is a basic E-Commerce backend built with Java and Spring Boot. It uses Maven for project management.

## Features
- **CRUD Operations**: The application provides complete Create, Read, Update, and Delete (CRUD) operations for managing products in the shopping cart.
- **Third-Party API Integration**: The application integrates with external APIs using [Fake store API](https://fakestoreapi.com/docs)
- **MySQL Database**: The application uses MySQL for data persistence.

## Implementation Details
- The application is built using the Spring Boot framework, Spring JPA features for developing web applications.
- CRUD operations are implemented in the `ProductService` and `ProductController` classes. These operations allow for the management of products in the shopping cart.
- The application integrates with third-party APIs to provide additional functionality. This integration is handled by FakeStoreProductService service.
- MySQL is used as the database for this application. The application interacted with the data from MySQL database using LocalProductService service.

This project demonstrates a strong understanding of Java, Spring Boot, and MySQL, as well as the ability to integrate with external APIs and perform CRUD operations.