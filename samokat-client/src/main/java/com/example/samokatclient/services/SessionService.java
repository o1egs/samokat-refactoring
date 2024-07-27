package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.exceptions.session.*;
import com.example.samokatclient.mappers.OrderMapper;
import com.example.samokatclient.mappers.UserMapper;
import com.example.samokatclient.redis.CurrentOrder;
import com.example.samokatclient.redis.Session;
import lombok.AllArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionService {
    private final static String HASH_KEY = "Session";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CartService cartService;
    private final UserService userService;
    private final UserMapper userMapper;

    public String createSession() {
        String sessionToken = UUID.randomUUID().toString();
        Session session = new Session(sessionToken, cartService.createCart());
        redisTemplate.opsForHash().put(HASH_KEY, sessionToken, session);
        return sessionToken;
    }

    public UserDto getSessionUser(String sessionToken) {
        Session session = getSession(sessionToken);
        if (session.getUser_id() != null) {
            return userMapper.toDto(userService.getUserById(session.getUser_id()));
        } else {
            throw new UserIsNotAuthorizedException();
        }
    }

    public void authorizeUser(String sessionToken, UserDto userDto) {
        Session session = getSession(sessionToken);
        if (session.getUser_id() == null) {
            userService.authorizeUser(userDto);
            session.setUser_id(userDto.phone_number);
            redisTemplate.opsForHash().put(HASH_KEY, sessionToken, session);
        } else {
            throw new UserIsAlreadyAuthorized();
        }
    }

    public void setAuthorizedUserName(String sessionToken, UserDto userDto) {
        Session session = getSession(sessionToken);
        if (session.getUser_id() != null) {
            userService.setUserName(session.getUser_id(), userDto.name);
        } else {
            throw new UserIsNotAuthorizedException();
        }
    }

    public AddressDto getAddress(String sessionToken) {
        Session session = getSession(sessionToken);
        String address_id = session.getAddress_id();
        if (address_id == null) {
            throw new AddressNotFoundForSessionException();
        } else {
            return userService.getAddress(address_id);
        }

    }

    public PaymentDto getPayment(String sessionToken) {
        Session session = getSession(sessionToken);
        String payment_id = session.getPayment_id();
        if (payment_id == null) {
            throw new PaymentNotFoundForSessionException();
        } else {
            return userService.getPayment(payment_id);
        }
    }

    public void setAddress(String sessionToken, String address_id) {
        Session session = getSession(sessionToken);
        session.setAddress_id(address_id);
        redisTemplate.opsForHash().put(HASH_KEY, sessionToken, session);
    }

    public void setPayment(String sessionToken, String payment_id) {
        Session session = getSession(sessionToken);
        session.setPayment_id(payment_id);
        putSession(session);
    }

    public List<OrderDto> getUserOrders(String sessionToken) {
        return userService.getUserOrders(getSessionUser(sessionToken).phone_number);
    }

    public OrderDto getUserOrderById(String sessionToken, String order_id) {
        return userService.getOrderById(getSessionUser(sessionToken).phone_number, order_id);
    }

    public List<AddressDto> getUserAddresses(String sessionToken) {
        return userService.getUserAddresses(this.getSessionUser(sessionToken).phone_number);
    }

    public List<PaymentDto> getUserPayments(String sessionToken) {
        return userService.getUserPayments(this.getSessionUser(sessionToken).phone_number);
    }

    public void createNewAddress(String sessionToken, AddressDto addressDto) {
        userService.createNewAddress(this.getSessionUser(sessionToken).phone_number, addressDto);
    }

    public void createNewPayment(String sessionToken, PaymentDto paymentDto) {
        userService.createNewPayment(this.getSessionUser(sessionToken).phone_number, paymentDto);
    }

    public CartDto getCart(String sessionToken) {
        Session session = getSession(sessionToken);
        return cartService.getCartDto(session.getCartToken());
    }

    public void addToCart(String sessionToken, Long product_id) {
        Session session = getSession(sessionToken);
        cartService.addToCart(session.getCartToken(), product_id);
    }

    public void deleteFromCart(String sessionToken, Long product_id) {
        Session session = getSession(sessionToken);
        cartService.deleteFromCart(session.getCartToken(), product_id);
    }

    public void clearCart(String sessionToken) {
        Session session = getSession(sessionToken);
        session.setCartToken(cartService.deleteCart(session.getCartToken()));
        putSession(session);
    }

    private Session getSession(String sessionToken) {
        Session session = (Session) redisTemplate.opsForHash().get(HASH_KEY, sessionToken);
        if (session == null) {
            throw new InvalidTokenException();
        } else {
            return session;
        }
    }

    private void putSession(Session session) {
        redisTemplate.opsForHash().put(HASH_KEY, session.getId(), session);
    }
}
