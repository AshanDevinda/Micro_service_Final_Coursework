package lk.ijse.ticket.service.service;


import lk.ijse.ticket.service.model.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO createTicket(TicketDTO newTicket);

    TicketDTO updateStatus(Long ticketId);

    List<TicketDTO> getAllTickets();

    TicketDTO getTicketeById(Long ticketId);
}
