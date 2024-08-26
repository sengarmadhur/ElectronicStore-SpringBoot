package com.ms.electronic.store.ElectronicStore.services.impl;

import com.ms.electronic.store.ElectronicStore.dtos.UserDto;
import com.ms.electronic.store.ElectronicStore.entities.User;
import com.ms.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ms.electronic.store.ElectronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        return entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserDto> getAllUser() {
        return null;
    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        return null;
    }

    private User dtoToEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .password(userDto.getPassword())
                .imageName(userDto.getImageName())
                .build();
    }

    private UserDto entityToDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .about(user.getAbout())
                .gender(user.getGender())
                .imageName(user.getImageName())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
