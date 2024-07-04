package com.ijse.user.service.repo;


import com.ijse.user.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity,Long> {

}
