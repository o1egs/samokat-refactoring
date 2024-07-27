package com.example.samokatclient.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash("Session")
@Getter
@Setter
public class Session implements Serializable {
    @Id
    private String id;
    private String user_id;
    private String cartToken;
    private String address_id;
    private String payment_id;
    private List<String> currentOrdersId;

    public Session(String sessionToken, String cartToken){
        this.id = sessionToken;
        this.cartToken = cartToken;
        this.user_id = null;
        this.address_id = null;
        this.payment_id = null;
        this.currentOrdersId = new ArrayList<>();
    }
}
