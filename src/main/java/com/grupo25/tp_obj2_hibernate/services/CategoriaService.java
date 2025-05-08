package com.grupo25.tp_obj2_hibernate.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.CategoriaDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoriaRepository.save(categoria);
        return new CategoriaDTO(categoria);
    }

    public List<CategoriaDTO> getCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

    public CategoriaDTO actualizarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(categoriaDTO.getId()).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoriaRepository.update(categoria);
        return new CategoriaDTO(categoria);
    }
}
