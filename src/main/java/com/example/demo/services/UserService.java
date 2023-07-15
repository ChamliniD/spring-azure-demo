package com.example.demo.services;

import com.example.demo.dtos.UserDTO;
import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
