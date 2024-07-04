package lk.ijse.ticket.service.service;

import lk.ijse.ticket.service.entity.TicketEntity;
import lk.ijse.ticket.service.exception.*;
import lk.ijse.ticket.service.mapping.Mapping;
import lk.ijse.ticket.service.model.TicketDTO;
import lk.ijse.ticket.service.model.UserDTO;
import lk.ijse.ticket.service.model.VehicleDTO;
import lk.ijse.ticket.service.repo.TicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceIMPL implements TicketService{

    private final RestTemplate restTemplate;
    private final TicketRepo ticketRepo;
    private final Mapping mapper;

    @Override
    public TicketDTO createTicket(TicketDTO newTicket) {
        if(restTemplate.getForObject("http://user-service/highway/api/v1/user/userById/"+ newTicket.getUserId(), UserDTO.class) == null) {
            throw new UserNotFountException();
        }
        if(restTemplate.getForObject("http://vehicle-service/highway/api/v1/vehicle/vehicleById/"+ newTicket.getVehicleNumber(), VehicleDTO.class) == null) {
            throw new VehicleNotFoundException();
        }
        try {
            newTicket.setStatus("pending");
            newTicket.setIssuedAt(LocalDateTime.now());
            return mapper.toTicketDTO(ticketRepo.save(mapper.toTicketEntity(newTicket)));
        }catch (Exception e) {
            throw new DataNotEffectException();
        }
    }

    @Override
    public TicketDTO updateStatus(Long ticketId) {
        TicketEntity ticketEntity = ticketRepo.findById(ticketId).orElseThrow(() -> new TicketNotFoundException());
        try {
            ticketEntity.setStatus("approved");
            return mapper.toTicketDTO(ticketEntity);
        }catch (Exception e) {
            throw new DataNotEffectException();
        }
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        try {
            return mapper.toTicketDTOList(ticketRepo.findAll());
        }catch (Exception e) {
            throw new DataReadException();
        }
    }

    @Override
    public TicketDTO getTicketeById(Long ticketId) {
        try {
            return mapper.toTicketDTO(ticketRepo.findById(ticketId).orElseThrow(() -> new TicketNotFoundException()));
        }catch (Exception e) {
            throw new DataReadException();
        }
    }
}
