package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.repository.EtiquetaRepository;

@Service
@RequiredArgsConstructor
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;

    public Etiqueta crearEtiqueta(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta modificarEtiqueta(int etiquetaId, String nombre) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));
        etiqueta.setNombre(nombre);
        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta obtenerEtiqueta(int etiquetaId) {
        return etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));
    }
}
