package com.ms.electronic.store.ElectronicStore.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;

    @Size(min = 3, max = 15, message = "Invalid Name !!")
    private String name;

    @NotBlank(message = "Password is required !!")
    private String password;

    //@Email(message = "Invalid User Email !!")
    @Pattern(regexp = "^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$", message = "Invalid User Email !!")
    @NotBlank(message = "Email is required !!")
    private String email;

    @Size(min = 4, max = 6, message = "Invalid gender !!")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;
    private String imageName;
}
