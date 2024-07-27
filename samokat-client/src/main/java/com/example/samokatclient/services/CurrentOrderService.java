package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.NewOrderDto;
import com.example.samokatclient.DTO.payment.PaymentInfoDto;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.exceptions.payment.BadConnectionToPaymentException;
import com.example.samokatclient.mappers.CartMapper;
import com.example.samokatclient.redis.CurrentOrder;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurrentOrderService {
    private final static String HASH_KEY = "CurrentOrderClient";
    private final RedisTemplate redisTemplate;
    private final CartMapper cartMapper;
    private final KafkaTemplate<String, NewOrderDto> kafkaTemplate;
    private final SessionService sessionService;
    private final OrderRepository orderRepository;

    public String createOrder(String sessionToken) {
        String orderToken = generateOrderToken();

        PaymentInfoDto paymentInfoDto = new PaymentInfoDto(
                sessionService.getPayment(sessionToken).card_number,
                sessionService.getPayment(sessionToken).expiration_date,
                sessionService.getPayment(sessionToken).cvc,
                sessionService.getCart(sessionToken).totalPrice,
                "http://localhost:8082/api"
        );
        String payment_code = initPayment(paymentInfoDto);
        NewOrderDto newOrderDto = new NewOrderDto(
                orderToken,
                cartMapper.toListOrderCartItem(sessionService.getCart(sessionToken)),
                sessionService.getCart(sessionToken).totalPrice,
                sessionService.getSessionUser(sessionToken).phone_number,
                sessionService.getAddress(sessionToken).id,
                sessionService.getPayment(sessionToken).id,
                LocalDateTime.now(),
                payment_code,
                "CREATED"
        );
        CurrentOrder currentOrder = new CurrentOrder(orderToken, "CREATED");
        putCurrentOrder(currentOrder);
        kafkaTemplate.send("newOrder", newOrderDto);
        return payment_code;
    }

    private String generateOrderToken() {
        String orderToken;
        Optional<Order> optionalOrder;
        do {
            orderToken = UUID.randomUUID().toString();
            optionalOrder = orderRepository.findById(orderToken);

        } while (optionalOrder.isPresent());
        return orderToken;
    }

    private String initPayment(PaymentInfoDto paymentInfoDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(paymentInfoDto);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8083/api/init"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            throw new BadConnectionToPaymentException();
        }
    }

    private void putCurrentOrder(CurrentOrder currentOrder) {
        redisTemplate.opsForHash().put(HASH_KEY, currentOrder.getId(), currentOrder);
    }


}

// TODO: 02.07.2024 Реализовать логику отмены заказа