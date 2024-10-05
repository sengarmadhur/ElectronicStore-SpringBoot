package com.ms.electronic.store.ElectronicStore.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotBlank(message = "Cart id is blank")
    private String cartId;

    @NotBlank(message = "user id is required")
    private String userId;

    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";

    private String billingAddress;

    @NotBlank(message = "Phone number is required")
    private String billingPhone;

    private String billingName;
}
