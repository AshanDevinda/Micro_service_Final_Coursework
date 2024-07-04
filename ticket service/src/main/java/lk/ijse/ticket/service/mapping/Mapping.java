package lk.ijse.ticket.service.mapping;

import lk.ijse.ticket.service.entity.TicketEntity;
import lk.ijse.ticket.service.model.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {

    private final ModelMapper mapper;

    public TicketEntity toTicketEntity(TicketDTO ticketDTO) {
        return mapper.map(ticketDTO, TicketEntity.class);
    }

    public TicketDTO toTicketDTO(TicketEntity ticketEntity) {
        return mapper.map(ticketEntity, TicketDTO.class);
    }

    public List<TicketDTO> toTicketDTOList(List<TicketEntity> allTicketEntity) {
        return mapper.map(allTicketEntity,List.class);
    }
}
