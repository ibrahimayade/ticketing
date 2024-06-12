package com.dreamsoft.ticketing.api.response;

import lombok.Data;

@Data
public class TicketingError {
    private String code;
    private String message;
}
