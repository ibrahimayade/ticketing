package com.dreamsoft.ticketing.service;

import com.dreamsoft.ticketing.dto.UserDTO;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.mapper.UserMapper;
import com.dreamsoft.ticketing.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getByUserId(Integer id) throws CustomException {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(CustomMessage.USER_NOT_FOUND));
    }

    public Optional<User> getByOptionnalUserId(Integer id) {
        return userRepository.findById(id);
    }

    public User getUserbyEmail(String email) throws CustomException {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomMessage.USER_NOT_FOUND));
    }

    public Optional<User> getOptionnalUserbyEmail(String email) throws CustomException {
        return userRepository.findByEmail(email);
    }

    public User saveUser(UserDTO userDTO) throws CustomException {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new CustomException(CustomMessage.USER_ALREADY_EXIST);
        }
        User user = userMapper.mapTOUser(userDTO);
        return userRepository.save(user);
    }

}
