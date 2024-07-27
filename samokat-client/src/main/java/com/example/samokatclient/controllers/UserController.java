package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Методы для работы с пользовательскими данными")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final SessionService sessionService;

    @Operation(
            summary = "Получить данные пользователя",
            description = "Данный метод выдаст данные авторизованного пользователя"
    )
    @GetMapping("/")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUser(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(sessionService.getSessionUser(token), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить историю заказов пользователя",
            description = "Данный метод выдаст историю заказов авторизованного пользователя"
    )
    @GetMapping("/orders")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserOrders(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(sessionService.getUserOrders(token), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить заказ пользователя",
            description = "Данный метод выдаст данные о заказе по id"
    )
    @GetMapping("/orders/{orderId}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserOrderById(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token,

            @Parameter(description = "id заказа")
            @PathVariable(value = "orderId") String orderId) {
        return new ResponseEntity<>(sessionService.getUserOrderById(token, orderId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить адреса пользователя",
            description = "Данный метод выдаст адреса, который сохранял пользователь"
    )
    @GetMapping("/addresses")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserAddresses(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(sessionService.getUserAddresses(token), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить способы оплаты пользователя",
            description = "Данный метод выдаст способы оплаты, который сохранял пользователь"
    )
    @GetMapping("/payments")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?>getUserPayments(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(sessionService.getUserPayments(token), HttpStatus.OK);
    }

    @Operation(
            summary = "Задать имя пользователю",
            description = "Поле номера телефона можно оставить пустым"
    )
    @PostMapping("/set-name")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> setName(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token,

            @RequestBody UserDto userDto){
        sessionService.setAuthorizedUserName(token, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить адрес",
            description = "Данный метод позволяет пользователю сохранить адрес для будущих заказов"
    )
    @PostMapping("/new-address")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> createNewAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token,

            @RequestBody AddressDto addressDto){
        sessionService.createNewAddress(token, addressDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить способ оплаты",
            description = "Данный метод позволяет пользователю сохранить способ оплаты для будущих заказов"
    )
    @PostMapping("/new-payment")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> createNewAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token,

            @RequestBody PaymentDto paymentDto){
        sessionService.createNewPayment(token, paymentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
