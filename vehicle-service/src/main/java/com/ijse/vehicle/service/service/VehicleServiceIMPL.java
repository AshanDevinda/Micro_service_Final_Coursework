package com.ijse.vehicle.service.service;

import com.ijse.vehicle.service.entity.VehicleEntity;
import com.ijse.vehicle.service.exception.DataNotEffectException;
import com.ijse.vehicle.service.exception.DataReadException;
import com.ijse.vehicle.service.exception.UserNotFountException;
import com.ijse.vehicle.service.exception.VehicleNotFoundException;
import com.ijse.vehicle.service.mapping.Mapping;
import com.ijse.vehicle.service.model.UserDTO;
import com.ijse.vehicle.service.model.VehicleDTO;
import com.ijse.vehicle.service.repo.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceIMPL implements VehicleService{

    private final Mapping mapper;
    private final RestTemplate restTemplate;
    private final VehicleRepo vehicleRepo;

    @Override
    public VehicleDTO registerVehicle(VehicleDTO newVehicle) {
        if(restTemplate.getForObject("http://user-service/highway/api/v1/user/userById/"+ newVehicle.getUserId(), UserDTO.class) == null) {
            throw new UserNotFountException();
        }
        try {
            return mapper.toVehicleDTO(vehicleRepo.save(mapper.toVehicleEntity(newVehicle)));
        }catch (Exception e) {
            throw new DataNotEffectException();
        }
    }

    @Override
    public VehicleDTO updateVehicle(Long vehicleId, VehicleDTO updatedVehicle) {
        if(restTemplate.getForObject("http://user-service/highway/api/v1/user/userById/"+ updatedVehicle.getUserId(), UserDTO.class) == null) {
            throw new UserNotFountException();
        }
        VehicleEntity vehicleEntity = vehicleRepo.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException());

        vehicleEntity.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
        vehicleEntity.setOwnerName(updatedVehicle.getOwnerName());
        vehicleEntity.setManufacturer(updatedVehicle.getManufacturer());
        vehicleEntity.setModel(updatedVehicle.getModel());
        vehicleEntity.setManufactureYear(updatedVehicle.getManufactureYear());
        vehicleEntity.setUserId(updatedVehicle.getUserId());

        return mapper.toVehicleDTO(vehicleEntity);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        try {
            return mapper.toVehicleDTOList(vehicleRepo.findAll());
        }catch (Exception e) {
            throw new DataReadException();
        }
    }

    @Override
    public VehicleDTO getVehicleById(Long vehicleId) {
        try {
            return mapper.toVehicleDTO(vehicleRepo.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException()));
        }catch (Exception e) {
            throw new DataReadException();
        }
    }
}
