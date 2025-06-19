package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.service.AreaService;
import com.grupo25.tp_obj2_hibernate.exception.AreaException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaRestController {

    private final AreaService areaService;

    @PostMapping("/crear")
    public ResponseEntity<Area> crearArea(@RequestParam String nombre) {
        log.debug("Creando nueva área con nombre: {}", nombre);
        try {
            Area areaCreada = areaService.crearArea(nombre);
            return ResponseEntity.ok(areaCreada);
        } catch (AreaException e) {
            log.error("Error al crear la área con nombre: {}", nombre, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> modificarArea(@PathVariable int id, @RequestParam String nombre) {
        log.debug("Modificando área con ID: {}", id);
        try {
            Area areaModificada = areaService.modificarArea(id, nombre);
            return ResponseEntity.ok(areaModificada);
        } catch (AreaException e) {
            log.error("Error al modificar el área con ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> obtenerArea(@PathVariable int id) {
        log.debug("Obteniendo área con ID: {}", id);
        try {
            Area area = areaService.obtenerArea(id);
            return ResponseEntity.ok(area);
        } catch (AreaException e) {
            log.error("Error al obtener el área con ID: {}", id, e);
            throw e;
        }
    }
}