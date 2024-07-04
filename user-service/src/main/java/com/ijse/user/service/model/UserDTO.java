package com.ijse.user.service.model;

import com.ijse.user_service.role.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @Null(message = "generate by database")
    private Long userId;
    @NotNull(message = "require username")
    private String username;
    @NotNull(message = "require password")
    private String password;
    @Email(message = "invalid email type")
    private String email;
}
