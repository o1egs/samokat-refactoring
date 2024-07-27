package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.cart.CartItem;
import com.example.samokatclient.DTO.cart.OrderCartItem;
import com.example.samokatclient.redis.Cart;
import com.example.samokatclient.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class CartMapper {
    private final ProductService productService;

    public CartDto cartToDto(Cart cart) {
        Map<Long, Integer> productsTreeMap = cart.getProducts();
        List<CartItem> cartItemList = new ArrayList<>();
        long totalCost = 0L;
        for (Map.Entry<Long, Integer> entry : productsTreeMap.entrySet()){
            CartItem cartItem = new CartItem(
                    productService.getProductById(entry.getKey()),
                    entry.getValue()
            );
            totalCost += productService.getProductById(entry.getKey()).price * entry.getValue();
            cartItemList.add(cartItem);
        }
        return new CartDto(cartItemList, totalCost);
    }

    public CartDto listOrderCartItemToDto(List<OrderCartItem> orderCartItemList){
        List<CartItem> cartItemList = new ArrayList<>();
        long totalCost = 0L;
        for (OrderCartItem orderCartItem : orderCartItemList){
            CartItem cartItem = new CartItem(
                    productService.getProductById(orderCartItem.product_id),
                    orderCartItem.count
            );
            totalCost += productService.getProductById(orderCartItem.product_id).price * orderCartItem.count;
            cartItemList.add(cartItem);
        }
        return new CartDto(cartItemList, totalCost);
    }

    public List<OrderCartItem> toListOrderCartItem(CartDto cartDto){
        List<OrderCartItem> orderCartItemList = new ArrayList<>();
        for (CartItem cartItem : cartDto.cartItemList){
            orderCartItemList.add(new OrderCartItem(cartItem.product.id, cartItem.count));
        }
        return orderCartItemList;
    }
}
