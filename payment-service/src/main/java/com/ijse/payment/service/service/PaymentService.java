package com.ijse.payment.service.service;

import com.ijse.payment.service.model.PaymentDTO;

public interface PaymentService {
    PaymentDTO makePayment(PaymentDTO paymentDTO);
}
