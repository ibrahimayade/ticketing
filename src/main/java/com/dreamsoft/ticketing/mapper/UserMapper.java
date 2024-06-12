package com.dreamsoft.ticketing.mapper;

import com.dreamsoft.ticketing.api.response.UserData;
import com.dreamsoft.ticketing.dto.UserDTO;
import com.dreamsoft.ticketing.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapTOUser(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
    }

    public UserData mapToUserData(User user, String  token){
        UserData data=new UserData();
        data.setToken(token);
        data.setId(user.getId());
        data.setUsername(user.getUsername());
        data.setEmail(user.getEmail());
        data.setRole(user.getRole().name());
        return data;
    }
}
