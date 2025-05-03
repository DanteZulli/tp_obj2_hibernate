package com.grupo25.tp_obj2_hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.RolDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Rol;
import com.grupo25.tp_obj2_hibernate.model.repositories.RolRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * roles.
 * 
 * @author Grupo 25
 */
@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    /**
     * Crea un nuevo rol en la base de datos.
     * 
     * @param rol El rol a crear.
     * @return El rol creado.
     * 
     * @author Dante Zulli
     */
    public RolDTO crearRol(Rol rol) {
        return new RolDTO(rolRepository.save(rol));
    }

    /**
     * Modifica un rol existente en la base de datos.
     * 
     * @param rolId  El ID del rol a modificar.
     * @param nombre El nuevo nombre del rol.
     * @return El rol modificado.
     * 
     * @throws RuntimeException Si no se encuentra el rol con el ID dado.
     * 
     * @author Dante Zulli
     */
    public RolDTO modificarRol(int rolId, String nombre) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));

        rol.setNombre(nombre);
        rolRepository.update(rol);

        return new RolDTO(rol);
    }

    /**
     * Elimina un rol de la base de datos.
     * 
     * @param rolId El ID del rol a eliminar.
     * 
     * @throws RuntimeException Si no se encuentra el rol con el ID dado.
     * 
     * @author Dante Zulli
     */
    public RolDTO obtenerRol(int rolId) {
        return new RolDTO(rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId)));
    }
}