package com.example.samokatclient.repositories.user;

import com.example.samokatclient.entities.user.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findByUserId(String user_id);
}
