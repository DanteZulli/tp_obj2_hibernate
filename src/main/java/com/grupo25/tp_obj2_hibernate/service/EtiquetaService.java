package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.repository.EtiquetaRepository;
import com.grupo25.tp_obj2_hibernate.exception.EtiquetaException;

@Service
@RequiredArgsConstructor
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;

    public Etiqueta crearEtiqueta(String nombre) {
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setNombre(nombre);
        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta modificarEtiqueta(int id, String nombre) {
        Etiqueta etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> new EtiquetaException("Etiqueta no encontrada"));
        etiqueta.setNombre(nombre);
        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta obtenerEtiqueta(int id) {
        return etiquetaRepository.findById(id)
                .orElseThrow(() -> new EtiquetaException("Etiqueta no encontrada"));
    }

    public java.util.List<Etiqueta> obtenerEtiquetas() {
        return etiquetaRepository.findAll();
    }
}
