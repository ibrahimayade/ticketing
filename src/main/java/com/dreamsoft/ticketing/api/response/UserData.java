package com.dreamsoft.ticketing.api.response;

import lombok.Data;

@Data
public class UserData {
    private Integer id;
    private String email;
    private String username;
    private String role;
    private String token;
}
