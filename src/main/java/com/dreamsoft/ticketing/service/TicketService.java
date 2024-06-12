package com.dreamsoft.ticketing.service;

import com.dreamsoft.ticketing.dto.TicketDTO;
import com.dreamsoft.ticketing.entity.Ticket;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.mapper.TicketMapper;
import com.dreamsoft.ticketing.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;

    public TicketService(TicketRepository ticketRepository, TicketMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
    }


    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Integer id) throws CustomException {
        return ticketRepository.findById(id).orElseThrow(() -> new CustomException(CustomMessage.TICKET_NOT_FOUND));
    }
    public Optional<Ticket> getOptionnalTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public Ticket getTicketByTitre(String titre) throws CustomException {
        return ticketRepository.findFirstByTitre(titre).orElseThrow(() -> new CustomException(CustomMessage.TICKET_NOT_FOUND));
    }

    public Optional<Ticket> getOptionnalTicketByTitre(String titre) throws CustomException {
        return ticketRepository.findFirstByTitre(titre);
    }

   /* public List<Ticket> getUserTickets(User user) {
        return ticketRepository.findByUser(user);
    }*/

    public Ticket saveTicketDTO(TicketDTO ticketDTO) {
        return ticketRepository.save(mapper.mapToTocket(ticketDTO));
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }
}
