package com.ms.electronic.store.ElectronicStore.controllers;

import com.ms.electronic.store.ElectronicStore.dtos.*;
import com.ms.electronic.store.ElectronicStore.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("Ordere deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/user/userId")
    public ResponseEntity<List<OrderDto>> getOrdersOfTheUser(@PathVariable String userId) {
        List<OrderDto> orders = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "billingName", required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
