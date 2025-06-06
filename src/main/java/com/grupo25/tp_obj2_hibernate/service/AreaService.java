package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.repository.AreaRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con las
 * áreas.
 * 
 * @author Grupo 25
 */
@Service
public class AreaService {

    private AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
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
    public Area crearArea(Area area) {
        return areaRepository.save(area);
    }

    /**
     * Modifica un área existente en la base de datos.
     * 
     * @param areaId El ID del área a modificar.
     * @param nombre El nuevo nombre del área.
     * @return El área modificada.
     * 
     * @throws RuntimeException Si no se encuentra el área con el ID dado.
     * 
     * @author Dante Zulli
     */
    public Area modificarArea(int areaId, String nombre) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));

        area.setNombre(nombre);
        return areaRepository.save(area);
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
    public Area obtenerArea(int areaId) {
        return areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));
    }
}
