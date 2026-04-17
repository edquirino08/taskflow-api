package com.taskflow.service;

import com.taskflow.common.exceptions.ValidationException;
import com.taskflow.controller.user.dto.CreateUserDto;
import com.taskflow.model.User;
import com.taskflow.repositories.user.UserRepository;

import jakarta.inject.Inject;

public class UserService {

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 100;
    private static final int AGE_MIN = 0;
    private static final int AGE_MAX = 150;
    private static final int GENDER_MAX_LENGTH = 30;

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDto createUserDto) {
        validate(createUserDto);

        User user = User.builder()
                .name(createUserDto.getName().trim())
                .age(createUserDto.getAge())
                .gender(createUserDto.getGender() == null ? null : createUserDto.getGender().trim())
                .build();
        userRepository.createUser(user);
    }

    private void validate(CreateUserDto dto) {
        if (dto == null) {
            throw new ValidationException("Request body is required");
        }

        String name = dto.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        String trimmedName = name.trim();
        if (trimmedName.length() < NAME_MIN_LENGTH || trimmedName.length() > NAME_MAX_LENGTH) {
            throw new ValidationException(
                    "Name must be between " + NAME_MIN_LENGTH + " and " + NAME_MAX_LENGTH + " characters");
        }

        int age = dto.getAge();
        if (age < AGE_MIN || age > AGE_MAX) {
            throw new ValidationException("Age must be between " + AGE_MIN + " and " + AGE_MAX);
        }

        String gender = dto.getGender();
        if (gender != null && gender.trim().length() > GENDER_MAX_LENGTH) {
            throw new ValidationException("Gender must be at most " + GENDER_MAX_LENGTH + " characters");
        }
    }
}
