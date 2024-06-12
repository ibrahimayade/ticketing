package com.dreamsoft.ticketing.service;

import com.dreamsoft.ticketing.dto.UserDTO;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.entity.enumerations.Role;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.mapper.UserMapper;
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
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Tester le cas ou l'ID du user n'existe pas en base ")
    public void getUserByID_withNonExisting_value() {
        when(userRepository.findById(100)).thenReturn(Optional.empty());
        final CustomException customException = Assertions.assertThrows(
                CustomException.class,
                () -> {
                    userService.getByUserId(100);
                }
        );
        assertEquals(CustomMessage.USER_NOT_FOUND.getMessage(), customException.getMessage());

    }


    @Test
    @DisplayName("Tester la methode findAll")
    public void getAllUsers()  {
        List<User> users=List.of(new User(10, "iyade@yahoo.Fr", "yade", "P@sser123", Role.ADMIN ),
                new User(11, "s.ngom@gmail.com", "ngom", "P@sser123" , Role.ADMIN),
                new User(12, "s.sidibe@gmail.com", "sidibe", "P@sser123" , Role.ADMIN));
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(3, userService.getAllUser().size());
    }
    @Test
    @DisplayName("Tester le cas ou l'ID du user existe  en base")
    public void getUserByIdwithExisting_value() throws CustomException {
        when(userRepository.findById(10)).thenReturn(Optional.of(new User(10, "iyade@yahoo.Fr", "yade", "P@sser123" , Role.ADMIN)));
        assertEquals("iyade@yahoo.Fr", userService.getByUserId(10).getEmail());
    }

    @Test
    @DisplayName("Tester le cas ou l'Email du user n'existe pas en base ")
    public void getUserByEmail_withNonExisting_value() {
        when(userRepository.findByEmail("iyade@yahoo.Fr")).thenReturn(Optional.empty());
        final CustomException customException = Assertions.assertThrows(
                CustomException.class,
                () -> {
                    userService.getUserbyEmail("iyade@yahoo.Fr");
                }
        );
        assertEquals(CustomMessage.USER_NOT_FOUND.getMessage(), customException.getMessage());

    }

    @Test
    @DisplayName("Tester le cas ou l'Email du user existe  en base")
    public void getUserByEmailwithExisting_value() throws CustomException {
        when(userRepository.findByEmail("iyade@yahoo.Fr")).thenReturn(Optional.of(new User(10, "iyade@yahoo.Fr", "yade", "P@sser123" , Role.ADMIN)));
        assertEquals(10, userService.getUserbyEmail("iyade@yahoo.Fr").getId());
    }

    @Test
    @DisplayName("tester la creation d'un nouveau user")
    public void save_user_withGoodParam() throws CustomException {
        UserDTO userDTO =new UserDTO("yade","iyade@yahoo.Fr","P@sser123" );
        User user=new User(10, "iyade@yahoo.Fr", "yade", "P@sser123", Role.ADMIN);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapTOUser(userDTO)).thenReturn(user);
        assertEquals(10, userService.saveUser(userDTO).getId());
    }

    @Test
    @DisplayName("tester la creation d'un  user existant")
    public void save_user_withExistingEmail() {
        UserDTO userDTO =new UserDTO("yade","iyade@yahoo.Fr","P@sser123" );
        User user=new User(10, "iyade@yahoo.Fr", "yade", "P@sser123", Role.ADMIN);
        when(userMapper.mapTOUser(userDTO)).thenReturn(user);
        when(userRepository.findByEmail("iyade@yahoo.Fr")).thenReturn(Optional.of(user));


        final CustomException customException = Assertions.assertThrows(
                CustomException.class,
                () -> {
                    userService.saveUser(userDTO);
                }
        );
        assertEquals(CustomMessage.USER_ALREADY_EXIST.getMessage(), customException.getMessage());
    }

}
