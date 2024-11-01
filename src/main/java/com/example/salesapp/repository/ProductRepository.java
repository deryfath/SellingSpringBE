package com.example.salesapp.repository;

import com.example.salesapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Optional: Method to find a product by name
    Product findByName(String name);
}
