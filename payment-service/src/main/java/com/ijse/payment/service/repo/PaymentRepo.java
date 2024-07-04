package com.ijse.payment.service.repo;


import com.ijse.payment.service.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentEntity,Long> {

}
