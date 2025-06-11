package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriasRestController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestParam String nombre, @RequestParam(required = false) String descripcion) {
        log.debug("Creando nueva categoría con nombre: {}", nombre);
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            Categoria categoriaCreada = categoriaService.crearCategoria(categoria);
            return ResponseEntity.ok(categoriaCreada);
        } catch (RuntimeException e) {
            log.error("Error al crear la categoría con nombre: {}", nombre, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{categoriaId}")
    public ResponseEntity<Categoria> modificarCategoria(@PathVariable int categoriaId, 
                                                      @RequestParam String nombre,
                                                      @RequestParam(required = false) String descripcion) {
        log.debug("Modificando categoría con ID: {}", categoriaId);
        try {
            Categoria categoriaModificada = categoriaService.modificarCategoria(categoriaId, nombre, descripcion);
            return ResponseEntity.ok(categoriaModificada);
        } catch (RuntimeException e) {
            log.error("Error al modificar la categoría con ID: {}", categoriaId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable int categoriaId) {
        try {
            Categoria categoria = categoriaService.obtenerCategoria(categoriaId);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            log.error("Error al obtener la categoría con ID: {}", categoriaId, e);
            return ResponseEntity.notFound().build();
        }
    }
}