package com.grupo25.tp_obj2_hibernate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleTicketException(TicketException ex, WebRequest request) {
        log.error("Error en operación de ticket: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Error en operación de ticket");
    }

    @ExceptionHandler(AreaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleAreaException(AreaException ex, WebRequest request) {
        log.error("Error en operación de área: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Error en operación de área");
    }

    @ExceptionHandler(CategoriaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCategoriaException(CategoriaException ex, WebRequest request) {
        log.error("Error en operación de categoría: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Error en operación de categoría");
    }

    @ExceptionHandler(ComentarioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleComentarioException(ComentarioException ex, WebRequest request) {
        log.error("Error en operación de comentario: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Error en operación de comentario");
    }

    @ExceptionHandler(EtiquetaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleEtiquetaException(EtiquetaException ex, WebRequest request) {
        log.error("Error en operación de etiqueta: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Error en operación de etiqueta");
    }

    @ExceptionHandler(RevisionesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleRevisionesException(RevisionesException ex, WebRequest request) {
        log.error("Error en operación de revisión: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Error en operación de revisión");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado");
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, String errorType) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", status.value());
        body.put("error", errorType);

        if (ex instanceof TicketException) {
            body.put("codigo", ((TicketException) ex).getCodigo());
        } else if (ex instanceof AreaException) {
            body.put("codigo", ((AreaException) ex).getCodigo());
        } else if (ex instanceof CategoriaException) {
            body.put("codigo", ((CategoriaException) ex).getCodigo());
        } else if (ex instanceof ComentarioException) {
            body.put("codigo", ((ComentarioException) ex).getCodigo());
        } else if (ex instanceof EtiquetaException) {
            body.put("codigo", ((EtiquetaException) ex).getCodigo());
        } else if (ex instanceof RevisionesException) {
            body.put("codigo", ((RevisionesException) ex).getCodigo());
        }

        return new ResponseEntity<>(body, status);
    }
}
