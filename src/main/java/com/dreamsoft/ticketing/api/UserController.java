package com.dreamsoft.ticketing.api;

import com.dreamsoft.ticketing.api.response.CustomeResponse;
import com.dreamsoft.ticketing.api.response.UserData;
import com.dreamsoft.ticketing.dto.LoginReqDTO;
import com.dreamsoft.ticketing.dto.UserDTO;
import com.dreamsoft.ticketing.entity.User;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.service.AuthService;
import com.dreamsoft.ticketing.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Gestion des utilisateurs")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("")
    @Operation(summary = "Endpoint pour récupérer la liste des utilisateurs")
    public ResponseEntity<CustomeResponse> getAll() {
        List<User> users = userService.getAllUser();
        final CustomeResponse response = new CustomeResponse();
        response.setData(users);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint pour récupérer un utilisateur à partir de son ID")
    public ResponseEntity<CustomeResponse> getUserById(@PathVariable(value = "id") final Integer id) throws CustomException {
        User user = userService.getByUserId(id);
        final CustomeResponse response = new CustomeResponse();
        response.setData(user);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Endpoint permettant la creation d'un utilisateur")
    public ResponseEntity<CustomeResponse> createUser(@RequestBody @Valid UserDTO request) throws CustomException {

        User user = userService.saveUser(request);
        final CustomeResponse response = new CustomeResponse();
        response.setData(user);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint permettant la modification d'un utilisateur")
    public ResponseEntity<CustomeResponse> updateUser(@PathVariable(value = "id") Integer id, @RequestBody @Valid UserDTO request) throws CustomException {

        Optional<User> optionalUser = userService.getByOptionnalUserId(id);
        if (!optionalUser.isPresent()) {
            throw new CustomException(CustomMessage.USER_NOT_FOUND);

        }
        User user = optionalUser.get();
        Optional<User> optionalUserByEmail = userService.getOptionnalUserbyEmail(request.getEmail());
        if (optionalUserByEmail.isPresent() && user != optionalUser.get()) {
            throw new CustomException(CustomMessage.NOT_ALLOWED);
        }
        final CustomeResponse response = new CustomeResponse();
        response.setData(user);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pours'authentifer")
    @PostMapping("/login")
    public ResponseEntity<CustomeResponse> login(@RequestBody @Valid LoginReqDTO loginRequest) throws CustomException {

        UserData userData = authService.login(loginRequest);
        final CustomeResponse response = new CustomeResponse();
        response.setData(userData);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }
}
