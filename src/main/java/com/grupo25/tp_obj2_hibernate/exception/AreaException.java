package com.grupo25.tp_obj2_hibernate.exception;

public class AreaException extends RuntimeException {
    private final String codigo;

    public AreaException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public AreaException(String mensaje) {
        super(mensaje);
        this.codigo = "AREA_ERROR";
    }

    public String getCodigo() {
        return codigo;
    }
} 