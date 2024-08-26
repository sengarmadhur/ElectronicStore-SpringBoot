package com.ms.electronic.store.ElectronicStore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String password;
    private String email;
    private String gender;
    private String about;
    private String imageName;
}
