package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.service.AreaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con áreas.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/areas")
public class AreaRestController {

    private final AreaService areaService;

    public AreaRestController (AreaService areaService) {
        this.areaService = areaService;
    }

    /**
     * Crea una nueva área.
     * 
     * @param area El área a crear
     * @return El área creada
     * 
     * @author Dante Zulli
     */
    @PostMapping
    public ResponseEntity<Area> crearArea(@RequestBody Area area) {
        Area areaCreada = areaService.crearArea(area);
        return ResponseEntity.ok(areaCreada);
    }

    /**
     * Modifica un área existente.
     * 
     * @param areaId  El ID del área a modificar
     * @param nombre El nuevo nombre del área
     * @return El área modificada
     * 
     * @throws RuntimeException si no se encuentra el área con el ID dado
     * 
     * @author Dante Zulli
     */
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

    /**
     * Obtiene un área por su ID.
     * 
     * @param areaId El ID del área a obtener
     * @return El área encontrada
     * 
     * @throws RuntimeException si no se encuentra el área con el ID dado
     * 
     * @author Dante Zulli
     */
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