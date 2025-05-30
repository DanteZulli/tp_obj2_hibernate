package com.grupo25.tp_obj2_hibernate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.CategoriaDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crear categoria
     * 
     * @param categoriaDTO
     * @return CategoriaDTO
     * 
     * @author Ariel Serato
     */
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoriaRepository.save(categoria);
        return new CategoriaDTO(categoria);
    }

    /**
     * Obtener todas las categorias
     * 
     * @return List<CategoriaDTO>
     * 
     * @author Ariel Serato
     */
    public List<CategoriaDTO> getCategorias() {
        return categoriaRepository.findAll().stream().map(CategoriaDTO::new).toList();
    }

    /**
     * Actualizar categoria
     * 
     * @param categoriaDTO
     * @return CategoriaDTO
     * 
     * @author Ariel Serato
     */
    public CategoriaDTO actualizarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(categoriaDTO.getId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoriaRepository.save(categoria);
        return new CategoriaDTO(categoria);
    }
}
