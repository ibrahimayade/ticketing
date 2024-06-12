package com.dreamsoft.ticketing.service;


import com.dreamsoft.ticketing.api.response.UserData;
import com.dreamsoft.ticketing.dto.LoginReqDTO;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.mapper.UserMapper;
import com.dreamsoft.ticketing.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public UserData login(LoginReqDTO loginReqDTO) throws CustomException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginReqDTO.getEmail(),
                            loginReqDTO.getPassword()
                    )
            );

            User user = userService.getUserbyEmail(loginReqDTO.getEmail());
            String token = jwtUtils.generateJwtToken(loginReqDTO.getEmail());
            return userMapper.mapToUserData(user, token);


        } catch (BadCredentialsException e) {

            throw new CustomException(CustomMessage.INVALID_CREDENTIALS);
        }
    }
}
