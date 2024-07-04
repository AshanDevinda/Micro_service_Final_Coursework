package lk.ijse.ticket.service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    @Null(message = "generate by database")
    private Long vehicleId;
    @NotNull(message = "require registration number")
    private String registrationNumber;
    @NotNull(message = "require owner name")
    private String ownerName;
    @NotNull(message = "require manufacturer")
    private String manufacturer;
    @NotNull(message = "require vehicle model")
    private String model;
    @NotNull(message = "require manufacture year")
    private Integer manufactureYear;
    @NotNull(message = "require user id")
    private Long userId;
}
