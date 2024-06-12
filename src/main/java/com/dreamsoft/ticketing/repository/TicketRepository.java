package com.dreamsoft.ticketing.repository;

import com.dreamsoft.ticketing.entity.Ticket;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByUser(User user);

    public Optional<Ticket> findFirstByTitre(String titre);

    public List<Ticket> findByStatus(Status status);

    public List<Ticket> findByUserAndStatus(User user, Status status);
}
