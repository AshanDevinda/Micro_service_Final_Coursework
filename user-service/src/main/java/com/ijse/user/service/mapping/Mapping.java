package com.ijse.user.service.mapping;

import com.ijse.user.service.entity.UserEntity;
import com.ijse.user.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapping {

    private final ModelMapper mapper;

    public UserEntity toUserEntity(UserDTO userDTO) {
        return mapper.map(userDTO, UserEntity.class);
    }

    public UserDTO toUserDTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }
}
