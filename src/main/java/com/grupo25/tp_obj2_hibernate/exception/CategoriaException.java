package com.grupo25.tp_obj2_hibernate.exception;

public class CategoriaException extends RuntimeException {
    private final String codigo;

    public CategoriaException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public CategoriaException(String mensaje) {
        super(mensaje);
        this.codigo = "CATEGORIA_ERROR";
    }

    public String getCodigo() {
        return codigo;
    }
} 