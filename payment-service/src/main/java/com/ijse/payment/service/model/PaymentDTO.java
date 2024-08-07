package com.ijse.payment.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    @Null(message = "generated by database")
    private Long paymentId;
    @NotNull(message = "require ticket id")
    private Long ticketId;
    @Null(message = "set using ticket service")
    private Double amount;
    @Null(message = "set by service")
    private LocalDateTime paymentDateTime;
}
