package com.grupo25.tp_obj2_hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.repository.CategoriaRepository;
import com.grupo25.tp_obj2_hibernate.exception.CategoriaException;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria crearCategoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        return categoriaRepository.save(categoria);
    }

    public Categoria obtenerCategoria(int id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoría no encontrada con ID: " + id));
    }

    public Categoria modificarCategoria(int id, String nombre, String descripcion) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoria no encontrada"));
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }
}
