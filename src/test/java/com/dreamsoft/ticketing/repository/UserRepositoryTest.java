package com.dreamsoft.ticketing.repository;

import com.dreamsoft.ticketing.entity.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase
@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
/*
@Sql({"/db/test/users-data.sql"})
@Sql(scripts = "/db/test/users-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
*/

public class UserRepositoryTest {
   @Autowired
    private  UserRepository userRepository;



    @Test
    @DisplayName("Tester la methode qui renvoie la liste complete ")
    public void findAll() {
        Assertions.assertEquals(4, userRepository.findAll().size());
    }

    @Test
    @DisplayName("Tester les cas ou l'ID du user n'existe pas ")
    public void findByID_With_Not_Existing_Row() {
        Optional<User> optionalUser = userRepository.findById(4000);
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("Tester les cas ou l'email du user n'existe pas ")
    public void findByEmail_With_Not_Existing_Row() {
        Optional<User> optionalUser = userRepository.findByEmail("yad.ibrahom@gmail.com");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("tester le cas ou l'email du user existe ")
    public void findByEmail_With_Existing_Row() {
        Optional<User> optionalUser = userRepository.findByEmail("a.wade@senegal.com");
        assertTrue(optionalUser.isPresent());
        Assertions.assertEquals("wade", optionalUser.get().getUsername());
    }

    @Test
    @DisplayName("tester le cas ou l'ID du user existe  ")
    public void findByID_With_Existing_Row() {
        Optional<User> optionalUser = userRepository.findById(2);
        assertTrue(optionalUser.isPresent());

    }



    @Test
    @DisplayName("Tester la creation d'un nouveau user avec de bons inputs")
    public void save_withValidRow() {
        User user1 = User.builder().
                username("tall").
                email("l.tall@senegal.com").build();
        user1 = userRepository.save(user1);
        Assertions.assertNotNull(user1.getId());
    }

    @Test
    @DisplayName("Tester la creation d'un nouveau user avec email existant")
    public void save_with_constraintViolation_no_uniqEmail() {
        User user1 = User.builder().
                username("sene").
                email("a.wade@senegal.com").build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user1);
            userRepository.flush();
        });
    }

}
