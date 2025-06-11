package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.repository.AreaRepository;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public Area crearArea(Area area) {
        return areaRepository.save(area);
    }

    public Area modificarArea(int areaId, String nombre) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));

        area.setNombre(nombre);
        return areaRepository.save(area);
    }

    public Area obtenerArea(int areaId) {
        return areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));
    }
}
