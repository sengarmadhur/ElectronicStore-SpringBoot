package com.ms.electronic.store.ElectronicStore.controllers;

import com.ms.electronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.ms.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.ms.electronic.store.ElectronicStore.dtos.CartDto;
import com.ms.electronic.store.ElectronicStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable int itemId, @PathVariable String userId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponseMessage msg = ApiResponseMessage.builder().message("Item is removed").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage msg = ApiResponseMessage.builder().message("Cart is cleared").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartForUser(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}




//        "productId": "1988d67d-2784-42db-ba11-a485880fbbaa",
//                "userId": "778ccaf7-bc1a-435c-ad9e-5d33a63917cf",