package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con categorías.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/categorias")
public class CategoriasRestController {

    private CategoriaService categoriaService;

    public CategoriasRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Crear categoría
     * 
     * @param Categoria
     * @return ResponseEntity con la categoría creada, o un error si no existe
     * 
     * @author Ariel Serato
     */
    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria cat = categoriaService.crearCategoria(categoria);
            return ResponseEntity.ok(cat);
        } catch (RuntimeException e) {
            log.error("Error al crear la categoría: {}", categoria, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualizar categoría
     * 
     * @param Categoria
     * @return ResponseEntity con la categoría actualizada, o un error si no existe
     * 
     * @author Ariel Serato
     */
    @PutMapping("/actualizar")
    public ResponseEntity<Categoria> actualizarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria cat = categoriaService.actualizarCategoria(categoria);
            return ResponseEntity.ok(cat);
        } catch (RuntimeException e) {
            log.error("Error al actualizar la categoría: {}", categoria, e);
            return ResponseEntity.notFound().build();
        }
    }
}