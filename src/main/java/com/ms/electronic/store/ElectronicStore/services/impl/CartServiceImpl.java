package com.ms.electronic.store.ElectronicStore.services.impl;

import com.ms.electronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.ms.electronic.store.ElectronicStore.dtos.CartDto;
import com.ms.electronic.store.ElectronicStore.entities.Cart;
import com.ms.electronic.store.ElectronicStore.entities.CartItem;
import com.ms.electronic.store.ElectronicStore.entities.Product;
import com.ms.electronic.store.ElectronicStore.entities.User;
import com.ms.electronic.store.ElectronicStore.exceptions.BadApiRequest;
import com.ms.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ms.electronic.store.ElectronicStore.repositories.CartItemRepository;
import com.ms.electronic.store.ElectronicStore.repositories.CartRepository;
import com.ms.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.ms.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ms.electronic.store.ElectronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {
            throw new BadApiRequest("Requested quantity is invalid");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in DB"));
        Cart cart;

        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException ex) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        List<CartItem> items = cart.getItems();
        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> updatedItems = items.stream().map(item -> {
           if (item.getProduct().getProductId().equals(productId)) {
               item.setQuantity(quantity);
               item.setTotalPrice(quantity*product.getDiscountedPrice());
               updated.set(true);
           }
           return  item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        if (!updated.get()) {
            CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getDiscountedPrice()).cart(cart).product(product).build();
            cart.getItems().add(cartItem);
        }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));
        return mapper.map(cart, CartDto.class);
    }
}
