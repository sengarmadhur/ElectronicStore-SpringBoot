package com.ms.electronic.store.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_password", length = 10)
    private String password;

    @Column(name = "user_email", unique = true)
    private String email;
    private String gender;

    @Column(length = 100)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
