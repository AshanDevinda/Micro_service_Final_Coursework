package com.ijse.user.service.controller;
import com.ijse.user.service.exception.DataNotEffectException;
import com.ijse.user.service.exception.DataReadException;
import com.ijse.user.service.exception.UserNotFountException;
import com.ijse.user.service.model.UserDTO;
import com.ijse.user.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/health")
    public String health() {
        logger.info("health endpoint was called...");
        return "user controller";
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserDTO new_user, Errors errors) {
        if(errors.hasFieldErrors()) {
            logger.error("detect field errors...");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            UserDTO savedUser = userService.registerUser(new_user);
            logger.info("saved new user...");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }catch (DataNotEffectException e) {
            logger.error("user not saved...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user not registered");
        }
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity updateProfile(@Valid @RequestBody UserDTO new_user, @PathVariable Long userId ,Errors errors) {
        if(errors.hasFieldErrors()) {
            logger.error("detect field errors...");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            UserDTO updatedUser = userService.updateProfile(userId, new_user);
            logger.info("user updated...");
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        }catch (UserNotFountException e) {
            logger.error("user not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not founded");
        }catch (DataNotEffectException e) {
            logger.error("user not updated...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user not updated");
        }
    }

    @GetMapping("/userById/{userId}")
    public ResponseEntity getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
        }catch (UserNotFountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("can't found user");
        }catch (DataReadException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error, can't get user");
        }
    }
}
