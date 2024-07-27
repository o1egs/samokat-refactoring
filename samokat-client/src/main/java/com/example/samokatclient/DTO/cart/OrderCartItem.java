package com.example.samokatclient.DTO.cart;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderCartItem {
    public Long product_id;
    public Integer count;

    public OrderCartItem(Long product_id, Integer count){
        this.product_id = product_id;
        this.count = count;
    }
}
