package com.dreamsoft.ticketing.repository;

import com.dreamsoft.ticketing.entity.Ticket;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.Status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles("test")
@AutoConfigureTestDatabase
@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")


public class TicketRepositoryTest {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  TicketRepository ticketRepository;

    @Test
    @DisplayName("Tester la methode qui renvoie la liste complete des tickets")
    public void findAll() {
        Assertions.assertEquals(9, ticketRepository.findAll().size());
    }


    @Test
    @DisplayName("Tester les cas ou l'ID du ticket n'existe pas ")
    public void findByID_With_Not_Existing_Row() {
        Optional<User> optionalUser = userRepository.findById(4000);
        assertTrue(optionalUser.isEmpty());
    }



    @Test
    @DisplayName("tester le cas ou le titre du ticket existe ")
    public void findByEmail_With_Existing_Row() {
        Optional<Ticket> optionalTicket = ticketRepository.findFirstByTitre("Ticket1");
        assertTrue(optionalTicket.isPresent());
        Assertions.assertEquals("description 1", optionalTicket.get().getDescription());
    }

    @Test
    @DisplayName("Tester la creation d'un nouveau ticket avec de bons inputs")
    public void save_withValidRow() {
        Ticket ticket = Ticket.builder().
                titre("TicketX").
                description("Description X").build();
        ticket = ticketRepository.save(ticket);
        Assertions.assertNotNull(ticket.getId());
    }

    @Test
    @DisplayName("Tester la creation d'un nouveau ticket avec titre existant")
    public void save_with_constraintViolation_no_uniqEmail() {
        Ticket ticket = Ticket.builder().
                titre("Ticket1").
                description("descwwwwwww").
                status(Status.COMPLETED).build();
               ;
        assertThrows(DataIntegrityViolationException.class, () -> {
            ticketRepository.save(ticket);
            userRepository.flush();
        });
    }
}
