package com.grupo25.tp_obj2_hibernate.services;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.EtiquetaDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.repositories.EtiquetaRepository;

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
    public EtiquetaDTO crearEtiqueta(EtiquetaDTO etiquetaDTO) {
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setNombre(etiquetaDTO.getNombre());
        etiquetaRepository.save(etiqueta);
        return new EtiquetaDTO(etiqueta);
    }
    
    /**
     * Actualizar etiqueta
     * 
     * @param etiquetaDTO
     * @return EtiquetaDTO
     * 
     * @author Ariel Serato
     */
    public EtiquetaDTO actualizarEtiqueta(EtiquetaDTO etiquetaDTO) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaDTO.getId()).orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));
        etiqueta.setNombre(etiquetaDTO.getNombre());
        etiquetaRepository.save(etiqueta);
        return new EtiquetaDTO(etiqueta);
    }
}
