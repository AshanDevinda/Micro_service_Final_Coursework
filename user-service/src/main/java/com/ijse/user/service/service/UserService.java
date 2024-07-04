package com.ijse.user.service.service;

import com.ijse.user.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserDTO registerUser(UserDTO newUser);

    UserDTO updateProfile(Long userId, UserDTO newUser);

    UserDTO getUserById(Long userId);
}

