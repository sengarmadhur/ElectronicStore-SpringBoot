package com.ms.electronic.store.ElectronicStore.dtos;

import com.ms.electronic.store.ElectronicStore.entities.Order;
import com.ms.electronic.store.ElectronicStore.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

    private OrderDto order;
}
