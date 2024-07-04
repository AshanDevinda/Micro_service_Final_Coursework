package com.ijse.payment.service.controller;

import com.ijse.payment.service.exception.DataReadException;
import com.ijse.payment.service.exception.TicketNotFoundException;
import com.ijse.payment.service.model.PaymentDTO;
import com.ijse.payment.service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentServiceController {
    private final Logger logger = LoggerFactory.getLogger(PaymentServiceController.class);
    private final PaymentService paymentService;

    @GetMapping("/health")
    public String health() {
        logger.info("health endpoint was called...");
        return "payment controller";
    }

    @PostMapping("/pay")
    public ResponseEntity makePayment(@Valid @RequestBody PaymentDTO paymentDTO, Errors errors) {
        if (errors.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errors.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.makePayment(paymentDTO));
        }catch (TicketNotFoundException e) {
            logger.error("ticket not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ticket not found");
        }catch (DataReadException e) {
            logger.error("payment not successfully");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("payment not successfully");
        }
    }
}
