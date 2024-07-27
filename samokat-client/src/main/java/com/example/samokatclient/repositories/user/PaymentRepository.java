package com.example.samokatclient.repositories.user;

import com.example.samokatclient.entities.user.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByUserId(String userId);
}
