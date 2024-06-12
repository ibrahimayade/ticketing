package com.dreamsoft.ticketing.service;
import com.dreamsoft.ticketing.entity.Ticket;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.entity.enumerations.Role;
import com.dreamsoft.ticketing.entity.enumerations.Status;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.mapper.TicketMapper;
import com.dreamsoft.ticketing.mapper.UserMapper;
import com.dreamsoft.ticketing.repository.TicketRepository;
import com.dreamsoft.ticketing.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TicketServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TicketMapper ticketMapper;
    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("Tester le cas ou l'ID du ticket n'existe pas en base ")
    public void getTicketByID_withNonExisting_value() {
        when(ticketRepository.findById(100)).thenReturn(Optional.empty());
        final CustomException customException = Assertions.assertThrows(
                CustomException.class,
                () -> {
                    ticketService.getTicketById(100);
                }
        );
        assertEquals(CustomMessage.TICKET_NOT_FOUND.getMessage(), customException.getMessage());

    }


    @Test
    @DisplayName("Tester la methode findAll")
    public void getAllTicket() {
        User user1 = new User(10, "iyade@yahoo.Fr", "yade", "P@sser123", Role.ADMIN);
        User user2 = new User(11, "s.ngom@gmail.com", "ngom", "P@sser123", Role.ADMIN);
        User user3 = new User(12, "s.sidibe@gmail.com", "sidibe", "P@sser123", Role.ADMIN);
        List<Ticket> tickets = List.of(
                Ticket.builder().
                        //  id(1001).
                                titre("TicketA").description("description A").status(Status.PENDING).user(user1).build(),
                Ticket.builder().
                        // id(1002).
                                titre("TicketB").description("description B").status(Status.PENDING).user(user2).build(),
                Ticket.builder().
                        //  id(1003).
                                titre("TicketC").description("description C").status(Status.PENDING).user(user3).build());

        when(ticketRepository.findAll()).thenReturn(tickets);
        assertEquals(3, ticketService.getAll().size());
    }

    @Test
    @DisplayName("Tester le cas ou l'ID du user existe  en base")
    public void getTicketByIdwithExisting_value() throws CustomException {
        User user=new User(10, "iyade@yahoo.Fr", "yade", "P@sser123", Role.ADMIN);
      Ticket ticket=  Ticket.builder(). titre("TicketA").description("description A").status(Status.PENDING).user(user).build();

        when(ticketRepository.findById(10)).thenReturn(Optional.of(ticket));
        assertEquals("TicketA", ticketService.getTicketById(10).getTitre());
    }

    @Test
    @DisplayName("Tester le cas ou le titre du ticket n'existe pas en base ")
    public void getByTitre_withNonExisting_value() {
        when(ticketRepository.findFirstByTitre("TicketA")).thenReturn(Optional.empty());
        final CustomException customException = Assertions.assertThrows(
                CustomException.class,
                () -> {
                    ticketService.getTicketByTitre("TicketA");
                }
        );
        assertEquals(CustomMessage.TICKET_NOT_FOUND.getMessage(), customException.getMessage());

    }

    @Test
    @DisplayName("Tester le cas ou le titre du ticket existe  en base")
    public void getByTitre_withExisting_value() throws CustomException {
        User user=new User(10, "iyade@yahoo.Fr", "yade", "P@sser123", Role.ADMIN);
        Ticket ticket=  Ticket.builder(). titre("TicketA").description("description A").status(Status.PENDING).user(user).build();

        when(ticketRepository.findFirstByTitre("TicketA")).thenReturn(Optional.of(ticket));
        assertEquals("description A", ticketService.getTicketByTitre("TicketA").getDescription());
    }

}
