package com.dreamsoft.ticketing.api;

import com.dreamsoft.ticketing.api.response.CustomeResponse;
import com.dreamsoft.ticketing.dto.TicketDTO;
import com.dreamsoft.ticketing.entity.Ticket;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.repository.UserRepository;
import com.dreamsoft.ticketing.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/tickets")
@Tag(name = "TICKETS API", description = "Gestion des tickets")

public class TicketController {
    private final TicketService ticketService;
    private final UserRepository userRepository;

    public TicketController(TicketService ticketService, UserRepository userRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }


    @GetMapping("")
    @Operation(summary = "Endpoint pour récupérer tous les tickets")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomeResponse> getAllTicket() {
        List<Ticket> tickets = ticketService.getAll();
        final CustomeResponse response = new CustomeResponse();
        response.setData(tickets);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Endpoint pour récupérer tous les tickets")
    public ResponseEntity<CustomeResponse> getTicketById(@PathVariable(value = "id") final Integer id) throws CustomException {
        Ticket ticket = ticketService.getTicketById(id);
        final CustomeResponse response = new CustomeResponse();
        response.setData(ticket);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Endpoint permettant la creation d'un ticket")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomeResponse> createTicket(@RequestBody @Valid TicketDTO request) throws CustomException {
        Optional<Ticket> optionalTicket = ticketService.getOptionnalTicketByTitre(request.getTitre());
        if (optionalTicket.isPresent()) {
            throw new CustomException(CustomMessage.TICKET_ALREADY_EXIST);
        }
        Ticket ticket = ticketService.saveTicketDTO(request);
        final CustomeResponse response = new CustomeResponse();
        response.setData(ticket);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint permettant la modification d'un ticket")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomeResponse> updateTicket(@PathVariable(value = "id") Integer id, @RequestBody @Valid TicketDTO request) throws CustomException {

        Ticket ticket = ticketService.getTicketById(id);
        Optional<Ticket> optionalTicketByTitre = ticketService.getOptionnalTicketByTitre(request.getTitre());
        if (optionalTicketByTitre.isPresent() && ticket != optionalTicketByTitre.get()) {
            throw new CustomException(CustomMessage.TICKET_NOT_FOUND);
        }
        ticket = ticketService.saveTicketDTO(request);
        final CustomeResponse response = new CustomeResponse();
        response.setData(ticket);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{id}/assign/{userId}")
    @Operation(summary = "Endpoint permettant l'assignation d'un ticket à un user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomeResponse> assignTicket(@PathVariable(value = "id") Integer id, @PathVariable(value = "userId") Integer userId) throws CustomException {

        Ticket ticket = ticketService.getTicketById(id);

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new CustomException(CustomMessage.USER_NOT_FOUND);

        }
        User user = optionalUser.get();
        ticket.setUser(user);
        ticket = ticketService.save(ticket);
        final CustomeResponse response = new CustomeResponse();
        response.setData(ticket);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint permettant la suppression n d'un ticket")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomeResponse> deleteTicket(@PathVariable(value = "id") Integer id) throws CustomException {

        Ticket ticket = ticketService.getTicketById(id);
        ticketService.delete(ticket);
        final CustomeResponse response = new CustomeResponse();
        response.setData(true);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }
}
