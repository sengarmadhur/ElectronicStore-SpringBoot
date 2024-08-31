package com.ms.electronic.store.ElectronicStore.services.impl;

import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ms.electronic.store.ElectronicStore.dtos.UserDto;
import com.ms.electronic.store.ElectronicStore.entities.User;
import com.ms.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ms.electronic.store.ElectronicStore.helper.Helper;
import com.ms.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ms.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

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

        User user = userRepository.findById(userId).orElseThrow(() -> {
            return new ResourceNotFoundException("User not found");
        });

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->{
            return new ResourceNotFoundException("User not found");
        });
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);

        return Helper.getPageableResponse(page, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {return new ResourceNotFoundException("User not found");});
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> {return new ResourceNotFoundException("User not found");});
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> entityToDto(user)).toList();
        return userDtos;
    }

    private User dtoToEntity(UserDto userDto) {
//        return User.builder()
//                .name(userDto.getName())
//                .userId(userDto.getUserId())
//                .email(userDto.getEmail())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .password(userDto.getPassword())
//                .imageName(userDto.getImageName())
//                .build();
        return mapper.map(userDto, User.class);
    }

    private UserDto entityToDto(User user) {
//        return UserDto.builder()
//                .name(user.getName())
//                .userId(user.getUserId())
//                .about(user.getAbout())
//                .gender(user.getGender())
//                .imageName(user.getImageName())
//                .password(user.getPassword())
//                .email(user.getEmail())
//                .build();
        return mapper.map(user, UserDto.class);
    }
}
