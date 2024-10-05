package com.ms.electronic.store.ElectronicStore.services.impl;

import ch.qos.logback.core.model.Model;
import com.ms.electronic.store.ElectronicStore.dtos.CreateOrderRequest;
import com.ms.electronic.store.ElectronicStore.dtos.OrderDto;
import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ms.electronic.store.ElectronicStore.entities.*;
import com.ms.electronic.store.ElectronicStore.exceptions.BadApiRequest;
import com.ms.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ms.electronic.store.ElectronicStore.helper.Helper;
import com.ms.electronic.store.ElectronicStore.repositories.CartRepository;
import com.ms.electronic.store.ElectronicStore.repositories.OrderRepository;
import com.ms.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ms.electronic.store.ElectronicStore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItem> items = cart.getItems();

        if (items.size() <= 0) {
            throw new BadApiRequest("Invalid number of items in cart");
        }

       Order order =  Order.builder().billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = items.stream().map(cartItem -> {
            orderAmount.set(orderAmount.get() + cartItem.getTotalPrice());
            return OrderItem.builder().quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getTotalPrice())
                    .order(order).build();

        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(orderAmount.get());
        cart.getItems().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
