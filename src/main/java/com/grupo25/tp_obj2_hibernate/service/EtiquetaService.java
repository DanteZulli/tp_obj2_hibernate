package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.repository.EtiquetaRepository;

@Service
public class EtiquetaService {

    private EtiquetaRepository etiquetaRepository;

    public EtiquetaService(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    /**
     * Crear etiqueta
     * 
     * @param etiquetaDTO
     * @return EtiquetaDTO
     * 
     * @author Ariel Serato
     */
    public Etiqueta crearEtiqueta(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    /**
     * Actualizar etiqueta
     * 
     * @param etiquetaDTO
     * @return EtiquetaDTO
     * 
     * @author Ariel Serato
     */
    public Etiqueta actualizarEtiqueta(Etiqueta etiqueta) {
        Etiqueta etiquetaDTO = etiquetaRepository.findById(etiqueta.getId())
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));
        etiqueta.setNombre(etiquetaDTO.getNombre());
        return etiquetaRepository.save(etiqueta);
    }
}
