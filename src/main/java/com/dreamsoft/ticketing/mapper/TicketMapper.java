package com.dreamsoft.ticketing.mapper;

import com.dreamsoft.ticketing.dto.TicketDTO;
import com.dreamsoft.ticketing.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket mapToTocket(TicketDTO dto) {
        return Ticket.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .build();
    }
}
