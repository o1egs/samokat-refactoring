package com.example.samokatclient.DTO.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentInfoDto {
    private String card_number;
    private String expiration_date;
    private Integer cvc;
    private Long totalPrice;
    private String url;
}
