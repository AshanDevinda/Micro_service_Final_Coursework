package com.ijse.vehicle.service.repo;

import com.ijse.vehicle.service.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<VehicleEntity,Long> {

}
