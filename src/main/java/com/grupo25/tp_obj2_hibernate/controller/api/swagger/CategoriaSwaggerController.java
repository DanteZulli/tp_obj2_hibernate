package com.grupo25.tp_obj2_hibernate.controller.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.model.dto.CategoriaDTO;
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
@RequestMapping("/api/swagger/categorias")
@RequiredArgsConstructor
@Tag(name = "Gestión de Categorías", description = "API para gestión de categorías usando DTOs con Record Class")
public class CategoriaSwaggerController {

    private final CategoriaService categoriaService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría usando DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría creada exitosamente", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CategoriaDTO> crearCategoria(
            @Parameter(description = "DTO con los datos de la nueva categoría", required = true) @RequestBody CategoriaDTO categoriaDTO) {
        log.debug("Creando nueva categoría con DTO: {}", categoriaDTO);
        try {
            Categoria categoriaCreada = categoriaService.crearCategoria(categoriaDTO.nombre(),
                    categoriaDTO.descripcion());
            CategoriaDTO responseDTO = new CategoriaDTO(categoriaCreada.getId(), categoriaCreada.getNombre(),
                    categoriaCreada.getDescripcion());
            return ResponseEntity.ok(responseDTO);
        } catch (CategoriaException e) {
            log.error("Error al crear la categoría con DTO: {}", categoriaDTO, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Recupera la información de una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada exitosamente", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriaDTO> obtenerCategoria(
            @Parameter(description = "ID de la categoría a obtener", required = true, example = "1") @PathVariable int id) {
        log.debug("Obteniendo categoría con ID: {}", id);
        try {
            Categoria categoria = categoriaService.obtenerCategoria(id);
            CategoriaDTO responseDTO = new CategoriaDTO(categoria.getId(), categoria.getNombre(),
                    categoria.getDescripcion());
            return ResponseEntity.ok(responseDTO);
        } catch (CategoriaException e) {
            log.error("Error al obtener la categoría con ID: {}", id, e);
            throw e;
        }
    }
}