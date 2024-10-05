package com.ms.electronic.store.ElectronicStore.services;

import com.ms.electronic.store.ElectronicStore.dtos.CreateOrderRequest;
import com.ms.electronic.store.ElectronicStore.dtos.OrderDto;
import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {
        OrderDto createOrder(CreateOrderRequest request);

        void removeOrder(String orderId);

        List<OrderDto> getOrdersOfUser(String userId);

        PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}
