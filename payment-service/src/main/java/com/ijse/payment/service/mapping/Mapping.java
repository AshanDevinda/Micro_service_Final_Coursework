package com.ijse.payment.service.mapping;

import com.ijse.payment.service.entity.PaymentEntity;
import com.ijse.payment.service.model.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {

    private final ModelMapper mapper;

    public PaymentEntity toPaymentEntity(PaymentDTO paymentDTO) {
        return mapper.map(paymentDTO, PaymentEntity.class);
    }

    public PaymentDTO toPaymentDTO(PaymentEntity paymentEntity) {
        return mapper.map(paymentEntity, PaymentDTO.class);
    }

    public List<PaymentDTO> toPaymentDTOList(List<PaymentEntity> allPaymentEntity) {
        return mapper.map(allPaymentEntity,List.class);
    }
}
