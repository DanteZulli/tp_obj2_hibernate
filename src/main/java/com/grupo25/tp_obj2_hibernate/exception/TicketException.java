package com.grupo25.tp_obj2_hibernate.exception;

import lombok.Getter;

@Getter
public class TicketException extends RuntimeException {

    private final String errorCode;

    public TicketException(String message) {
        super(message);
        this.errorCode = "TICKET_ERROR";
    }

    public TicketException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TicketException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "TICKET_ERROR";
    }

    public TicketException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
