package com.ms.electronic.store.ElectronicStore.services;

import com.ms.electronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.ms.electronic.store.ElectronicStore.dtos.CartDto;

public interface CartService {
    //add item to cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    void removeItemFromCart(String userId, int cartItem);

    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
