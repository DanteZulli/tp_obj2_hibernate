package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.service.EtiquetaService;
import com.grupo25.tp_obj2_hibernate.exception.EtiquetaException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/etiquetas")
@RequiredArgsConstructor
public class EtiquetaRestController {

    private final EtiquetaService etiquetaService;
    
    @PostMapping("/crear")
    public ResponseEntity<Etiqueta> crearEtiqueta(@RequestParam String nombre) {
        log.debug("Creando nueva etiqueta con nombre: {}", nombre);
        try {
            Etiqueta etiquetaCreada = etiquetaService.crearEtiqueta(nombre);
            return ResponseEntity.ok(etiquetaCreada);
        } catch (EtiquetaException e) {
            log.error("Error al crear la etiqueta: {}", nombre, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etiqueta> modificarEtiqueta(@PathVariable int id, @RequestParam String nombre) {
        log.debug("Modificando etiqueta con ID: {}", id);
        try {
            Etiqueta etiqueta = etiquetaService.modificarEtiqueta(id, nombre);
            return ResponseEntity.ok(etiqueta);
        } catch (EtiquetaException e) {
            log.error("Error al actualizar la etiqueta: {}", nombre, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etiqueta> obtenerEtiqueta(@PathVariable int id) {
        log.debug("Obteniendo etiqueta con ID: {}", id);
        try {
            Etiqueta etiqueta = etiquetaService.obtenerEtiqueta(id);
            return ResponseEntity.ok(etiqueta);
        } catch (EtiquetaException e) {
            log.error("Error al obtener la etiqueta con ID: {}", id, e);
            throw e;
        }
    }
}