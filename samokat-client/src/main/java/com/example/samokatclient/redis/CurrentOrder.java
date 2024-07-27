package com.example.samokatclient.redis;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.order.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("CurrentOrderClient")
@AllArgsConstructor
@Getter
@Setter
public class CurrentOrder implements Serializable {
    private String id;
    private String status;
}

