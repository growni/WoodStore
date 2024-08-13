package com.WoodStore.services.impl;

import com.WoodStore.entities.UserEntity;
import com.WoodStore.entities.dtos.RegisterDto;
import com.WoodStore.exceptions.UserError;
import com.WoodStore.repositories.UserRepository;
import com.WoodStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.WoodStore.constants.Constants.*;
import static com.WoodStore.constants.Constants.USER_PASSWORD_MISSING_SPECIAL_REGEX;
import static com.WoodStore.messages.Errors.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_ERROR, username)));
    }

    @Override
    public void changePassword(RegisterDto registerDto) {
        UserEntity user = getByUsername(registerDto.getUsername());

        registerDto.validatePassword(registerDto.getNewPassword());

        user.setPassword(passwordEncoder.encode(registerDto.getNewPassword()));
        this.userRepository.save(user);
    }

    @Override
    public void changeUsername(RegisterDto registerDto) {
        UserEntity user = getByUsername(registerDto.getUsername());

        registerDto.validateUsername(registerDto.getNewUsername());

        user.setUsername(registerDto.getNewUsername());
        this.userRepository.save(user);
    }


}
