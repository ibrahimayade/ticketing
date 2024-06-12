package com.dreamsoft.ticketing;

import com.dreamsoft.ticketing.entity.Ticket;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.Role;
import com.dreamsoft.ticketing.entity.enumerations.Status;
import com.dreamsoft.ticketing.repository.TicketRepository;
import com.dreamsoft.ticketing.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
    @SpringBootApplication
    public class TicketingApplication {

        public static void main(String[] args) {
            SpringApplication.run(com.dreamsoft.ticketing.TicketingApplication.class, args);
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        CommandLineRunner commandLineRunner(UserRepository userRepository, TicketRepository ticketRepository, PasswordEncoder passwordEncoder) {
            return args -> {
                User user1 = User.builder().
                        //id(101).
                                username("senghor").
                        password(passwordEncoder.encode("P@sser123")).
                        email("lss.seghor@senegal.com").
                        role(Role.ADMIN)
                        .build();

                User user2 = User.builder().
                        // id(102).
                                username("diouf").
                        password(passwordEncoder.encode("P@sser123")).
                        email("a.diouf@senegal.com")
                        .role(Role.USER).
                        build();
                User user3 = User.builder().
                        //id(103).
                                username("wade").
                        password(passwordEncoder.encode("P@sser123")).
                        email("a.wade@senegal.com").
                        role(Role.ADMIN).
                        build();
                User user4 = User.builder().
                        // id(104).
                                username("sall").
                        password(passwordEncoder.encode("P@sser123")).
                        email("m.sall@senegal.com").
                        role(Role.USER).
                        build();
                List<User> users = List.of(user1, user2, user3, user4);
                userRepository.saveAll(users);

                List<Ticket> tickets = List.of(
                        Ticket.builder().
                                //  id(1001).
                                        titre("Ticket1"). description("description 1").status(Status.PENDING).user(user1).build(),
                        Ticket.builder().
                                // id(1002).
                                        titre("Ticket2").description("description 2").status(Status.PENDING).user(user2).build(),
                        Ticket.builder().
                                //  id(1003).
                                        titre("Ticket3").description("description 3").status(Status.PENDING).user(user3).build(),
                        Ticket.builder().
                                //id(1004).
                                        titre("Ticket4").description("description 4").status(Status.PENDING).user(user4).build(),
                        Ticket.builder().
                                //id(1005).
                                        titre("Ticket5").description("description 5").status(Status.COMPLETED).user(user2).build(),
                        Ticket.builder().
                                // id(1006).
                                        titre("Ticket6").description("description 6").status(Status.CANCELED).user(user1).build(),
                        Ticket.builder().
                                // id(1007).
                                        titre("Ticket7").description("description 7").status(Status.COMPLETED).user(user2).build(),
                        Ticket.builder().
                                //  id(1008).
                                        titre("Ticket8").description("description 8").status(Status.CANCELED).user(user1).build(),
                        Ticket.builder().
                                // id(1009).
                                        titre("Ticket9").description("description 9").status(Status.COMPLETED).user(user4).build()
                );
                ticketRepository.saveAll(tickets);

            };
        }

    }
