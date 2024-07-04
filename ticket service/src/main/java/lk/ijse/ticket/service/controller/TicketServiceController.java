package lk.ijse.ticket.service.controller;

import jakarta.validation.Valid;
import lk.ijse.ticket.service.exception.*;
import lk.ijse.ticket.service.model.TicketDTO;
import lk.ijse.ticket.service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketServiceController {
    private final Logger logger = LoggerFactory.getLogger(TicketServiceController.class);
    private final TicketService ticketService;

    @GetMapping("/health")
    public String health() {
        logger.info("health endpoint was called...");
        return "ticket controller";
    }

    @PostMapping("/create")
    public ResponseEntity createTicket(@Valid @RequestBody TicketDTO new_ticket, Errors errors) {
        if(errors.hasFieldErrors()) {
            logger.error("detect field errors...");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            TicketDTO savedTicket = ticketService.createTicket(new_ticket);
            logger.info("saved new ticket...");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
        }catch (VehicleNotFoundException e) {
            logger.error("vehicle not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vehicle not founded");
        }catch (UserNotFountException e) {
            logger.error("user not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not founded");
        }catch (DataNotEffectException e) {
            logger.error("ticket not created...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ticket not created");
        }
    }

    @PatchMapping("/statusUpdate/{ticketId}")
    public ResponseEntity updateStatus(@PathVariable Long ticketId) {
        try {
            TicketDTO updatedTicket = ticketService.updateStatus(ticketId);
            logger.info("ticket status updated...");
            return ResponseEntity.status(HttpStatus.OK).body(updatedTicket);
        }catch (TicketNotFoundException e) {
            logger.error("ticket not founded...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ticket not founded");
        }catch (DataNotEffectException e) {
            logger.error("ticket status not updated...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("status not updated");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllTickets() {
        try {
            List<TicketDTO> ticketList = ticketService.getAllTickets();
            return ResponseEntity.status(HttpStatus.OK).body(ticketList);
        }catch (DataReadException e) {
            logger.error("can't get tickets data...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("can't get tickets data, try again");
        }
    }

    @GetMapping("/ticketById/{ticketId}")
    public ResponseEntity getTicketById(@PathVariable Long ticketId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ticketService.getTicketeById(ticketId));
        }catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("can't found ticket");
        }catch (DataReadException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error, can't get ticket");
        }
    }
}
