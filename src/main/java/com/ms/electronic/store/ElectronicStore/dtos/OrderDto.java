package com.ms.electronic.store.ElectronicStore.dtos;

import com.ms.electronic.store.ElectronicStore.entities.OrderItem;
import com.ms.electronic.store.ElectronicStore.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";

    private int totalAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate=new Date();

    private Date deliveredDate;

    private UserDto user;

    private List<OrderItemDto> orderItems = new ArrayList<>();
}
