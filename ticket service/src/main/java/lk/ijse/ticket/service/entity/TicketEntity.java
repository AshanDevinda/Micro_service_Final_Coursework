package lk.ijse.ticket.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ticket")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double fineAmount;

    @Column(nullable = false)
    private String userId;
}
