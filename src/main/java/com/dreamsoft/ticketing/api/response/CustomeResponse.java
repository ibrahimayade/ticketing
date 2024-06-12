package com.dreamsoft.ticketing.api.response;

import lombok.Data;

@Data
public class CustomeResponse {
    private boolean success = true;
    private Object data;
    private TicketingError error;
}
