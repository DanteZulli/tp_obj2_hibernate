package com.grupo25.tp_obj2_hibernate.exception;

public class TicketException extends RuntimeException {
    private final String codigo;

    public TicketException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public TicketException(String mensaje) {
        super(mensaje);
        this.codigo = "TICKET_ERROR";
    }

    public String getCodigo() {
        return codigo;
    }
}
