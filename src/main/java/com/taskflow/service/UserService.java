package com.taskflow.service;

import com.taskflow.controller.user.dto.CreateUserDto;
import com.taskflow.model.User;
import com.taskflow.repositories.user.UserRepository;

import jakarta.inject.Inject;

public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .name(createUserDto.getName())
                .age(createUserDto.getAge())
                .gender(createUserDto.getGender())
                .build();
        userRepository.createUser(user);
    }
}
