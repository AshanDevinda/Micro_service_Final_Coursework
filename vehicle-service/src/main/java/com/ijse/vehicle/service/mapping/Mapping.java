package com.ijse.vehicle.service.mapping;


import com.ijse.vehicle.service.entity.VehicleEntity;
import com.ijse.vehicle.service.model.VehicleDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {

    private final ModelMapper mapper;

    public VehicleEntity toVehicleEntity(VehicleDTO vehicleDTO) {
        return mapper.map(vehicleDTO, VehicleEntity.class);
    }

    public VehicleDTO toVehicleDTO(VehicleEntity vehicleEntity) {
        return mapper.map(vehicleEntity, VehicleDTO.class);
    }

    public List<VehicleDTO> toVehicleDTOList(List<VehicleEntity> allVehicleEntity) {
        return mapper.map(allVehicleEntity,List.class);
    }
}
