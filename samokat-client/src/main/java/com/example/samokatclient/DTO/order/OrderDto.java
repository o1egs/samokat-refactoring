package com.example.samokatclient.DTO.order;

import com.example.samokatclient.DTO.cart.CartDto;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
public class OrderDto {
    public String id;
    public CartDto cartDto;
    public Long totalPrice;
    public AddressDto addressDto;
    public PaymentDto paymentDto;
    public LocalDateTime orderDateTime;
    public String status;
}
