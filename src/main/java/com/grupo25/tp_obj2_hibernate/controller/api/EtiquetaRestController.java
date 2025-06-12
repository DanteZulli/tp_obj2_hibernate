package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.service.EtiquetaService;

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
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setNombre(nombre);
            Etiqueta etiquetaCreada = etiquetaService.crearEtiqueta(etiqueta);
            return ResponseEntity.ok(etiquetaCreada);
        } catch (RuntimeException e) {
            log.error("Error al crear la etiqueta: {}", nombre, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{etiquetaId}")
    public ResponseEntity<Etiqueta> modificarEtiqueta(@PathVariable int etiquetaId, @RequestParam String nombre) {
        log.debug("Modificando etiqueta con ID: {}", etiquetaId);
        try {
            Etiqueta etiqueta = etiquetaService.modificarEtiqueta(etiquetaId, nombre);
            return ResponseEntity.ok(etiqueta);
        } catch (RuntimeException e) {
            log.error("Error al actualizar la etiqueta: {}", nombre, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{etiquetaId}")
    public ResponseEntity<Etiqueta> obtenerEtiqueta(@PathVariable int etiquetaId) {
        log.debug("Obteniendo etiqueta con ID: {}", etiquetaId);
        try {
            Etiqueta etiqueta = etiquetaService.obtenerEtiqueta(etiquetaId);
            return ResponseEntity.ok(etiqueta);
        } catch (RuntimeException e) {
            log.error("Error al obtener la etiqueta con ID: {}", etiquetaId, e);
            return ResponseEntity.notFound().build();
        }
    }
    
}