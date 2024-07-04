package com.ijse.payment.service.service;

import com.ijse.payment.service.exception.TicketNotFoundException;
import com.ijse.payment.service.mapping.Mapping;
import com.ijse.payment.service.model.PaymentDTO;
import com.ijse.payment.service.model.TicketDTO;
import com.ijse.payment.service.repo.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceIMPL implements PaymentService{

    private final RestTemplate restTemplate;
    private final PaymentRepo paymentRepo;
    private final Mapping mapping;

    @Override
    public PaymentDTO makePayment(PaymentDTO paymentDTO) {
        TicketDTO ticketDTO = restTemplate.getForObject("http://ticket-service/highway/api/v1/ticket/ticketById/"+ paymentDTO.getTicketId(), TicketDTO.class);

        if(ticketDTO == null) {
            throw new TicketNotFoundException();
        }

//        String url = "http://localhost:8080/statusUpdate/{ticketId}";
//        Long ticketId = ticketDTO.getTicketId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<TicketDTO> response = restTemplate.exchange(
                "http://ticket-service/highway/api/v1/ticket/statusUpdate/"+ paymentDTO.getTicketId(),
                HttpMethod.PATCH,
                requestEntity,
                TicketDTO.class,
                ticketDTO.getTicketId()
        );


//        TicketDTO updatedTicketDTO = restTemplate.getForObject("http://ticket-service/highway/api/v1/ticket/statusUpdate/"+ paymentDTO.getTicketId(),TicketDTO.class);

        paymentDTO.setAmount(ticketDTO.getFineAmount());
        paymentDTO.setPaymentDateTime(LocalDateTime.now());
        return mapping.toPaymentDTO(paymentRepo.save(mapping.toPaymentEntity(paymentDTO)));
    }
}
