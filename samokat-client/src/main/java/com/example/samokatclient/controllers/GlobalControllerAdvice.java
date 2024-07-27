package com.example.samokatclient.controllers;

import com.example.samokatclient.exceptions.cart.CartNotFoundException;
import com.example.samokatclient.exceptions.cart.ProductNotFoundInCartException;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.CurrentOrderNotFoundException;
import com.example.samokatclient.exceptions.order.OrderNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.exceptions.product.CategoryNotFoundException;
import com.example.samokatclient.exceptions.product.ProductNotFoundException;
import com.example.samokatclient.exceptions.session.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFoundException ex){
        return new ResponseEntity<>("Корзины для данной сессии не существует", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundInCartException.class)
    public ResponseEntity<?> handleProductNotFoundInCartException(ProductNotFoundInCartException ex){
        return new ResponseEntity<>("Такого товара в корзине нет", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleAddressNotFoundException(AddressNotFoundException ex){
        return new ResponseEntity<>("Адрес не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handlePaymentNotFoundException(PaymentNotFoundException ex){
        return new ResponseEntity<>("Способ оплаты не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex){
        return new ResponseEntity<>("Заказ не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException ex){
        return new ResponseEntity<>("Категория не найдена", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex){
        return new ResponseEntity<>("Продукт не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex){
        return new ResponseEntity<>("Неверный ключ сессии", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIsAlreadyAuthorized.class)
    public ResponseEntity<?> handleUserIsAlreadyAuthorized(UserIsAlreadyAuthorized ex){
        return new ResponseEntity<>("Пользователь уже авторизован", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIsNotAuthorizedException.class)
    public ResponseEntity<?> handleUserIsNotAuthorizedException(UserIsNotAuthorizedException ex){
        return new ResponseEntity<>("Пользователь не авторизован", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrentOrderNotFoundException.class)
    public ResponseEntity<?> handleCurrentOrderNotFoundException(CurrentOrderNotFoundException ex){
        return new ResponseEntity<>("Текущий заказ не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundForSessionException.class)
    public ResponseEntity<?> handleAddressNotFoundForSessionException(AddressNotFoundForSessionException ex){
        return new ResponseEntity<>("Адрес не установлен для сессии", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentNotFoundForSessionException.class)
    public ResponseEntity<?> handlePaymentNotFoundForSessionException(PaymentNotFoundForSessionException ex){
        return new ResponseEntity<>("Способ оплаты не установлен для сессии", HttpStatus.NOT_FOUND);
    }
}
