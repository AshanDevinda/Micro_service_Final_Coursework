package lk.ijse.ticket.service.repo;

import lk.ijse.ticket.service.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<TicketEntity,Long> {

}
