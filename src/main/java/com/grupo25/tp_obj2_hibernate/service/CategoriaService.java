package com.grupo25.tp_obj2_hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.repository.CategoriaRepository;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria obtenerCategoria(int categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));
    }

    public Categoria modificarCategoria(int categoriaId, String nombre, String descripcion) {
        Categoria cat = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        cat.setNombre(nombre);
        cat.setDescripcion(descripcion);
        return categoriaRepository.save(cat);
    }

    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }
}
