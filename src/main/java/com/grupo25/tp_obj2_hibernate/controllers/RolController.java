package com.grupo25.tp_obj2_hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Rol;
import com.grupo25.tp_obj2_hibernate.model.services.RolService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con roles.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    /**
     * Crea un nuevo rol.
     * 
     * @param rol El rol a crear
     * @return El rol creado
     * 
     * @author Dante Zulli
     */
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol rolCreado = rolService.crearRol(rol);
        return ResponseEntity.ok(rolCreado);
    }

    /**
     * Modifica un rol existente.
     * 
     * @param rolId  El ID del rol a modificar
     * @param nombre El nuevo nombre del rol
     * @return El rol modificado
     * 
     * @throws RuntimeException si no se encuentra el rol con el ID dado
     * 
     * @author Dante Zulli
     */
    @PutMapping("/{rolId}")
    public ResponseEntity<Rol> modificarRol(@PathVariable int rolId, @RequestParam String nombre) {
        try {
            Rol rolModificado = rolService.modificarRol(rolId, nombre);
            return ResponseEntity.ok(rolModificado);
        } catch (RuntimeException e) {
            log.error("Error al modificar el rol con ID: {}", rolId, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un rol por su ID.
     * 
     * @param rolId El ID del rol a obtener
     * @return El rol encontrado
     * 
     * @throws RuntimeException si no se encuentra el rol con el ID dado
     * 
     * @author Dante Zulli
     */
    @GetMapping("/{rolId}")
    public ResponseEntity<Rol> obtenerRol(@PathVariable int rolId) {
        try {
            Rol rol = rolService.obtenerRol(rolId);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException e) {
            log.error("Error al obtener el rol con ID: {}", rolId, e);
            return ResponseEntity.notFound().build();
        }
    }
}