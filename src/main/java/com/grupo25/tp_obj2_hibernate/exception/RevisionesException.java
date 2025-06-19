package com.grupo25.tp_obj2_hibernate.exception;

public class RevisionesException extends RuntimeException {
    private final String codigo;

    public RevisionesException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public RevisionesException(String mensaje) {
        super(mensaje);
        this.codigo = "REVISION_ERROR";
    }

    public String getCodigo() {
        return codigo;
    }
}