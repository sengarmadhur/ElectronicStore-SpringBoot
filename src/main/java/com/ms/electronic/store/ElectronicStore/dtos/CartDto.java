package com.ms.electronic.store.ElectronicStore.dtos;

import com.ms.electronic.store.ElectronicStore.entities.CartItem;
import com.ms.electronic.store.ElectronicStore.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;

    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> items = new ArrayList<>();
}
