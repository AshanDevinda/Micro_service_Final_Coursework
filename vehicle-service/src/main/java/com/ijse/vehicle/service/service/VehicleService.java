package com.ijse.vehicle.service.service;

import com.ijse.vehicle.service.model.VehicleDTO;

import java.util.List;

public interface VehicleService {
    VehicleDTO registerVehicle(VehicleDTO newVehicle);

    VehicleDTO updateVehicle(Long vehicleId, VehicleDTO newVehicle);

    List<VehicleDTO> getAllVehicles();

    VehicleDTO getVehicleById(Long vehicleId);
}
