package com.WoodStore.services.impl;

import com.WoodStore.entities.Role;
import com.WoodStore.entities.UserEntity;
import com.WoodStore.entities.dtos.UserDto;
import com.WoodStore.exceptions.RoleError;
import com.WoodStore.exceptions.UserError;
import com.WoodStore.repositories.RoleRepository;
import com.WoodStore.repositories.UserRepository;
import com.WoodStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.WoodStore.messages.Errors.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
    public void changePassword(UserDto registerDto) {
        UserEntity user = getByUsername(registerDto.getUsername());

        registerDto.validatePassword(registerDto.getNewPassword());

        user.setPassword(passwordEncoder.encode(registerDto.getNewPassword()));
        this.userRepository.save(user);
    }

    @Override
    public void changeUsername(UserDto registerDto) {
        UserEntity user = getByUsername(registerDto.getUsername());

        registerDto.validateUsername(registerDto.getNewUsername());

        user.setUsername(registerDto.getNewUsername());
        this.userRepository.save(user);
    }

    @Override
    public void addRole(UserDto userDto) {
        Role role = this.roleRepository.findByName(userDto.getNewRoleName()).orElseThrow(() -> new RoleError(String.format(ROLE_NOT_FOUND_ERROR, userDto.getNewRoleName())));

        if(role.getName().equals("ADMIN")) {
            throw new UserError(String.format(INVALID_ROLE_PROMOTION_HIERARCHY, role.getName()));
        }

        UserEntity user = getByUsername(userDto.getUsername());

        user.getRoles().add(role);
        this.userRepository.save(user);
    }

    @Override
    public void removeRole(UserDto userDto) {
        Role role = this.roleRepository.findByName(userDto.getNewRoleName()).orElseThrow(() -> new RoleError(String.format(ROLE_NOT_FOUND_ERROR, userDto.getNewRoleName())));

        if(role.getName().equals("ADMIN")) {
            throw new UserError(String.format(INVALID_ROLE_PROMOTION_HIERARCHY, role.getName()));
        }

        UserEntity user = this.userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new UserError(String.format(USERNAME_NOT_FOUND_ERROR, userDto.getUsername())));

        List<Role> userRoles = user.getRoles().stream().filter(r -> r.getName().equals(role.getName())).toList();

        if(userRoles.size() > 0) {
            user.getRoles().remove(role);

        }

        if(user.getRoles().size() == 0) {
            Role userRole = this.roleRepository.findByName("USER").get();
            user.getRoles().add(userRole);
        }

        this.userRepository.save(user);
    }


}
