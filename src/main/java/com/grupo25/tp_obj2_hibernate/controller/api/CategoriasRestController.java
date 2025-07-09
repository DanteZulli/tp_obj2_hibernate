package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;
import com.grupo25.tp_obj2_hibernate.exception.CategoriaException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Gestión de Categorías", description = "API para gestionar categorías de tickets del sistema de soporte técnico")
public class CategoriasRestController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría para clasificar tickets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Categoria> crearCategoria(
            @Parameter(description = "Nombre de la nueva categoría", required = true, example = "Hardware")
            @RequestParam String nombre, 
            @Parameter(description = "Descripción opcional de la categoría", required = false, example = "Problemas relacionados con hardware")
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
    @Operation(summary = "Modificar categoría existente", description = "Modifica el nombre y/o descripción de una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría modificada exitosamente",
                    content = @Content(schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Categoria> modificarCategoria(
            @Parameter(description = "ID de la categoría a modificar", required = true, example = "1")
            @PathVariable int id, 
            @Parameter(description = "Nuevo nombre de la categoría", required = true, example = "Hardware y Periféricos")
            @RequestParam String nombre,
            @Parameter(description = "Nueva descripción de la categoría", required = false, example = "Problemas relacionados con hardware y dispositivos periféricos")
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
    @Operation(summary = "Obtener categoría por ID", description = "Recupera la información de una categoría específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada exitosamente",
                    content = @Content(schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Categoria> obtenerCategoria(
            @Parameter(description = "ID de la categoría a obtener", required = true, example = "1")
            @PathVariable int id) {
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