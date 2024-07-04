package com.ijse.vehicle.service.controller;

import com.ijse.vehicle.service.exception.DataNotEffectException;
import com.ijse.vehicle.service.exception.DataReadException;
import com.ijse.vehicle.service.exception.UserNotFountException;
import com.ijse.vehicle.service.exception.VehicleNotFoundException;
import com.ijse.vehicle.service.model.VehicleDTO;
import com.ijse.vehicle.service.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleServiceController {
    private final Logger logger = LoggerFactory.getLogger(VehicleServiceController.class);
    private final VehicleService vehicleService;

    @GetMapping("/health")
    public String health() {
        logger.info("health endpoint was called...");
        return "vehicle controller";
    }

    @PostMapping("/register")
    public ResponseEntity registerVehicle(@Valid @RequestBody VehicleDTO new_vehicle, Errors errors) {
        if(errors.hasFieldErrors()) {
            logger.error("detect field errors...");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            VehicleDTO savedVehicle = vehicleService.registerVehicle(new_vehicle);
            logger.info("saved new vehicle...");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        }catch (UserNotFountException e) {
            logger.error("user not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not founded");
        }catch (DataNotEffectException e) {
            logger.error("vehicle not saved...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("vehicle not registered");
        }
    }

    @PatchMapping("/update/{vehicleId}")
    public ResponseEntity updateProfile(@Valid @RequestBody VehicleDTO new_vehicle, @PathVariable Long vehicleId ,Errors errors) {
        if(errors.hasFieldErrors()) {
            logger.error("detect field errors...");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            VehicleDTO updatedVehicle = vehicleService.updateVehicle(vehicleId,new_vehicle);
            logger.info("vehicle updated...");
            return ResponseEntity.status(HttpStatus.OK).body(updatedVehicle);
        }catch (VehicleNotFoundException e) {
            logger.error("vehicle not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vehicle not founded");
        }catch (UserNotFountException e) {
            logger.error("user not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not founded");
        }catch (DataNotEffectException e) {
            logger.error("vehicle not updated...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("vehicle not updated");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllVehicles() {
        try {
            List<VehicleDTO> vehicleList = vehicleService.getAllVehicles();
            return ResponseEntity.status(HttpStatus.OK).body(vehicleList);
        }catch (DataReadException e) {
            logger.error("can't get vehicles data...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("can't get vehicles data, try again");
        }
    }

    @GetMapping("/vehicleById/{vehicleId}")
    public ResponseEntity getVehicleById(@PathVariable Long vehicleId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleById(vehicleId));
        }catch (VehicleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("can't found vehicle");
        }catch (DataReadException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error, can't get vehicle");
        }
    }
}
