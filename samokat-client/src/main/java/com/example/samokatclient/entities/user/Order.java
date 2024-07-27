package com.example.samokatclient.entities.user;

import com.example.samokatclient.DTO.cart.OrderCartItem;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class Order {
    @Id
    private String id;

    private List<OrderCartItem> orderCartItemList;
    private Long totalPrice;
    @Indexed
    private String userId;
    private String address_id;
    private String payment_id;

    private LocalDateTime orderDateTime;
    private String status;
}
