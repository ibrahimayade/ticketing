package com.dreamsoft.ticketing.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "username must be provided")
    private String username;
    @NotBlank(message = "email must be provided")
    private String email;

    @NotBlank(message = "password must be provided")
    private String password;
}
