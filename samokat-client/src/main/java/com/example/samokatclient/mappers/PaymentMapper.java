package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.entities.user.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment fromDto(PaymentDto paymentDto){
        Payment payment = new Payment();
        payment.setCard_number(paymentDto.card_number);
        payment.setExpiration_date(paymentDto.expiration_date);
        payment.setCvc(paymentDto.cvc);
        return payment;
    }

    public PaymentDto toDto(Payment payment){
        return new PaymentDto(payment);
    }
}
