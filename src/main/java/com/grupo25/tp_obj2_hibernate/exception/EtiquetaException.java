package com.grupo25.tp_obj2_hibernate.exception;

public class EtiquetaException extends RuntimeException {
    private final String codigo;

    public EtiquetaException(String mensaje, String codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public EtiquetaException(String mensaje) {
        super(mensaje);
        this.codigo = "ETIQUETA_ERROR";
    }

    public String getCodigo() {
        return codigo;
    }
} 