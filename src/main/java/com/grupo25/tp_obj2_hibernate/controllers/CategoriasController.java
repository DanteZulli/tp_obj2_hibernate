package com.grupo25.tp_obj2_hibernate.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.dto.CategoriaDTO;
import com.grupo25.tp_obj2_hibernate.services.CategoriaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con categorías.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {
    @Autowired
    private CategoriaService categoriaService;

    /**
     * Crear categoría
     * 
     * @param categoriaDTO
     * @return ResponseEntity con la categoría creada, o un error si no existe
     * 
     * @author Ariel Serato
     */
    @PostMapping("/crear")
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO categoria = categoriaService.crearCategoria(categoriaDTO);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            log.error("Error al crear la categoría: {}", categoriaDTO, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualizar categoría
     * 
     * @param categoriaDTO
     * @return ResponseEntity con la categoría actualizada, o un error si no existe
     * 
     * @author Ariel Serato
     */
    @PutMapping("/actualizar")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO categoria = categoriaService.actualizarCategoria(categoriaDTO);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            log.error("Error al actualizar la categoría: {}", categoriaDTO, e);
            return ResponseEntity.notFound().build();
        }
    }
}