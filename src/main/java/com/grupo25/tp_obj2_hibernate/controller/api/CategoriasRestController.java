package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;
import com.grupo25.tp_obj2_hibernate.exception.CategoriaException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriasRestController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(
            @RequestParam String nombre, 
            @RequestParam(required = false) String descripcion) {
        log.debug("Creando nueva categoría con nombre: {}", nombre);
        try {
            Categoria categoriaCreada = categoriaService.crearCategoria(nombre, descripcion);
            return ResponseEntity.ok(categoriaCreada);
        } catch (CategoriaException e) {
            log.error("Error al crear la categoría con nombre: {}", nombre, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> modificarCategoria(
            @PathVariable int id, 
            @RequestParam String nombre,
            @RequestParam(required = false) String descripcion) {
        log.debug("Modificando categoría con ID: {}", id);
        try {
            Categoria categoriaModificada = categoriaService.modificarCategoria(id, nombre, descripcion);
            return ResponseEntity.ok(categoriaModificada);
        } catch (CategoriaException e) {
            log.error("Error al modificar la categoría con ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable int id) {
        log.debug("Obteniendo categoría con ID: {}", id);
        try {
            Categoria categoria = categoriaService.obtenerCategoria(id);
            return ResponseEntity.ok(categoria);
        } catch (CategoriaException e) {
            log.error("Error al obtener la categoría con ID: {}", id, e);
            throw e;
        }
    }
}