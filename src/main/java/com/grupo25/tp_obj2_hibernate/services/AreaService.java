package com.grupo25.tp_obj2_hibernate.services;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.AreaDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.repositories.AreaRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con las áreas.
 * 
 * @author Grupo 25
 */
@Service
public class AreaService {

    private AreaRepository areaRepository;

    public AreaService (AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    /**
     * Crea una nueva área en la base de datos.
     * 
     * @param area El área a crear.
     * @return El área creada.
     * 
     * @author Dante Zulli
     */
    public AreaDTO crearArea(Area area) {
        return new AreaDTO(areaRepository.save(area));
    }

    /**
     * Modifica un área existente en la base de datos.
     * 
     * @param areaId  El ID del área a modificar.
     * @param nombre El nuevo nombre del área.
     * @return El área modificada.
     * 
     * @throws RuntimeException Si no se encuentra el área con el ID dado.
     * 
     * @author Dante Zulli
     */
    public AreaDTO modificarArea(int areaId, String nombre) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));

        area.setNombre(nombre);
        areaRepository.save(area);

        return new AreaDTO(area);
    }

    /**
     * Obtiene un área de la base de datos.
     * 
     * @param areaId El ID del área a obtener.
     * 
     * @throws RuntimeException Si no se encuentra el área con el ID dado.
     * 
     * @author Dante Zulli
     */
    public AreaDTO obtenerArea(int areaId) {
        return new AreaDTO(areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId)));
    }
}
