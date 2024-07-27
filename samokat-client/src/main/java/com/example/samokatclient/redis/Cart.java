package com.example.samokatclient.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Map;

@RedisHash("Cart")
@Getter
@Setter
@Builder
public class Cart implements Serializable {
    @Id
    private String id;
    private Map<Long, Integer> products;
}
