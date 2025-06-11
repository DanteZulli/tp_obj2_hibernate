package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.service.AreaService;

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
            Area area = new Area();
            area.setNombre(nombre);
            Area areaCreada = areaService.crearArea(area);
            return ResponseEntity.ok(areaCreada);
        } catch (RuntimeException e) {
            log.error("Error al crear la área con nombre: {}", nombre, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{areaId}")
    public ResponseEntity<Area> modificarArea(@PathVariable int areaId, @RequestParam String nombre) {
        try {
            Area areaModificada = areaService.modificarArea(areaId, nombre);
            return ResponseEntity.ok(areaModificada);
        } catch (RuntimeException e) {
            log.error("Error al modificar el área con ID: {}", areaId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<Area> obtenerArea(@PathVariable int areaId) {
        try {
            Area area = areaService.obtenerArea(areaId);
            return ResponseEntity.ok(area);
        } catch (RuntimeException e) {
            log.error("Error al obtener el área con ID: {}", areaId, e);
            return ResponseEntity.notFound().build();
        }
    }
}