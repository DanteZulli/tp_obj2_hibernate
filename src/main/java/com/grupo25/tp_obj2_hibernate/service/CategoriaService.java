package com.grupo25.tp_obj2_hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crear categoria
     * 
     * @param categoria
     * @return Categoria
     * 
     * @author Ariel Serato
     */
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * Obtener todas las categorias
     * 
     * @return List<Categoria>
     * 
     * @author Ariel Serato
     */
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    /**
     * Actualizar categoria
     * 
     * @param categoria
     * @return Categoria
     * 
     * @author Ariel Serato
     */
    public Categoria actualizarCategoria(Categoria categoria) {
        Categoria cat = categoriaRepository.findById(categoria.getId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        cat.setNombre(categoria.getNombre());
        cat.setDescripcion(categoria.getDescripcion());
        return categoriaRepository.save(cat);
    }

    /**
     * Obtener categoria por ID
     * 
     * @param id El ID de la categoría
     * @return Categoria
     * 
     * @author Grupo 25
     */
    public Categoria getCategoriaPorId(int id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }
}
