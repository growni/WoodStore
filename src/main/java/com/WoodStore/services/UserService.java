package com.WoodStore.services;

import com.WoodStore.entities.UserEntity;
import com.WoodStore.entities.dtos.UserDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {
    void deleteUser(Long userId);
    List<UserEntity> getAllUsers();
    UserEntity getByUsername(String username);
    void changePassword(UserDto userDto);
    void changeUsername(UserDto userDto);
    void addRole(UserDto userDto);
    void removeRole(UserDto userDto);
}
