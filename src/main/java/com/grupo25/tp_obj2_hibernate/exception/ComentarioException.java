package com.grupo25.tp_obj2_hibernate.exception;

public class ComentarioException extends RuntimeException {
    private final String codigo;

    public ComentarioException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public ComentarioException(String mensaje) {
        super(mensaje);
        this.codigo = "COMENTARIO_ERROR";
    }

    public String getCodigo() {
        return codigo;
    }
} 