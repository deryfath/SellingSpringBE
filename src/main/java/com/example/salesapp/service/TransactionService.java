package com.example.salesapp.service;

import com.example.salesapp.model.Transaction;
import com.example.salesapp.repository.TransactionRepository;
import com.example.salesapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    public Transaction createTransaction(Transaction transaction) {
        // Update product stock
        Product product = productRepository.findById(transaction.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() < transaction.getQuantity()) {
            throw new RuntimeException("Insufficient stock.");
        }
        product.setStock(product.getStock() - transaction.getQuantity());
        productRepository.save(product);

        transaction.setCreatedAt(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsBetween(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByCreatedAtBetween(start, end);
    }
}
