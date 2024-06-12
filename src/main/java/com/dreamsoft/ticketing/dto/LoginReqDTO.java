package com.dreamsoft.ticketing.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReqDTO {
    @NotBlank(message = "usename must be provided")
    private String email;

    @NotBlank(message = "password must be provided")
    private String password;
}
