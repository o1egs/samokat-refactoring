package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CartMapper cartMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.id = order.getId();
        orderDto.cartDto = cartMapper.listOrderCartItemToDto(order.getOrderCartItemList());
        orderDto.totalPrice = order.getTotalPrice();
        Optional<Address> optionalAddress = addressRepository.findById(order.getAddress_id());
        if (optionalAddress.isPresent()) {
            orderDto.addressDto = addressMapper.toDto(optionalAddress.get());
        } else {
            throw new AddressNotFoundException();
        }
        Optional<Payment> optionalPayment = paymentRepository.findById(order.getPayment_id());
        if (optionalPayment.isPresent()) {
            orderDto.paymentDto = paymentMapper.toDto(optionalPayment.get());
        } else {
            throw new AddressNotFoundException();
        }
        orderDto.orderDateTime = order.getOrderDateTime();
        orderDto.status = order.getStatus();
        return orderDto;
    }
}
