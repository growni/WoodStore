package com.WoodStore.services;

import com.WoodStore.entities.UserEntity;
import com.WoodStore.entities.dtos.RegisterDto;

import java.util.List;

public interface UserService {
    void deleteUser(Long userId);
    List<UserEntity> getAllUsers();
    UserEntity getByUsername(String username);
    void changePassword(RegisterDto registerDto);
    void changeUsername(RegisterDto registerDto);
}
