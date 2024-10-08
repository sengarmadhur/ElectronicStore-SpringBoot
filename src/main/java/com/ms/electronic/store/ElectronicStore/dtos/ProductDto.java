package com.ms.electronic.store.ElectronicStore.dtos;

import com.ms.electronic.store.ElectronicStore.entities.Category;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
    private String productId;

    private String title;

    private String description;

    private int price;
    private int discountedPrice;

    private int quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;

    private String productImage;
    private CategoryDto category;
}
