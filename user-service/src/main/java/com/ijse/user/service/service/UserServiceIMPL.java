package com.ijse.user.service.service;

import com.ijse.user.service.entity.UserEntity;
import com.ijse.user.service.exception.DataNotEffectException;
import com.ijse.user.service.exception.DataReadException;
import com.ijse.user.service.exception.UserNotFountException;
import com.ijse.user.service.mapping.Mapping;
import com.ijse.user.service.model.UserDTO;
import com.ijse.user.service.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService{

    private final Mapping mapping;
    private final UserRepo userRepo;

    @Override
    public UserDTO registerUser(UserDTO newUser) {
        try {
            return mapping.toUserDTO(userRepo.save(mapping.toUserEntity(newUser)));
        }catch (Exception e) {
            throw new DataNotEffectException();
        }
    }

    @Override
    public UserDTO updateProfile(Long userId, UserDTO newUser) {
        UserEntity userEntity = userRepo.findById(userId).orElseThrow(() -> new UserNotFountException());
        try {
            userEntity.setEmail(newUser.getEmail());
            userEntity.setPassword(newUser.getPassword());
            userEntity.setUsername(newUser.getUsername());
            return mapping.toUserDTO(userEntity);
        }catch (Exception e) {
            throw new DataNotEffectException();
        }
    }

    @Override
    public UserDTO getUserById(Long userId) {
        try {
            return mapping.toUserDTO(userRepo.findById(userId).orElseThrow(() -> new UserNotFountException()));
        }catch (Exception e) {
           throw new DataReadException();
        }
    }
}

//        return restTemplate.getForObject("http://item-service/api/v1/item/message", String.class);