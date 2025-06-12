package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.repository.AreaRepository;
import com.grupo25.tp_obj2_hibernate.exception.AreaException;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public Area crearArea(String nombre) {
        Area area = new Area();
        area.setNombre(nombre);
        return areaRepository.save(area);
    }

    public Area modificarArea(int id, String nombre) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new AreaException("Área no encontrada con ID: " + id));

        area.setNombre(nombre);
        return areaRepository.save(area);
    }

    public Area obtenerArea(int id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new AreaException("Área no encontrada con ID: " + id));
    }
}
