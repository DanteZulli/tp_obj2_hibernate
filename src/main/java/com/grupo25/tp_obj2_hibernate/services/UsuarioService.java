package com.grupo25.tp_obj2_hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.ClienteDTO;
import com.grupo25.tp_obj2_hibernate.model.dto.TecnicoDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.repositories.ClienteRepository;
import com.grupo25.tp_obj2_hibernate.repositories.DireccionRepository;
import com.grupo25.tp_obj2_hibernate.repositories.RolRepository;
import com.grupo25.tp_obj2_hibernate.repositories.TecnicoRepository;
import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;
import com.grupo25.tp_obj2_hibernate.model.entities.Rol;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * usuarios.
 * 
 * @author Grupo 25
 */
@Service
public class UsuarioService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private RolRepository rolRepository;

    /**
     * Crea un nuevo cliente en la base de datos.
     * 
     * @param nombre     El nombre del cliente.
     * @param email      El email del cliente.
     * @param plan       El plan del cliente.
     * @param particular Indica si el cliente es particular o no.
     * @return El cliente creado.
     * 
     * @author Dante Zulli
     */
    public ClienteDTO crearCliente(String nombre, String email, String plan, boolean particular) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPlan(plan);
        cliente.setParticular(particular);
        return new ClienteDTO(clienteRepository.save(cliente));
    }

    /**
     * Actualiza los datos de un cliente dado su ID.
     * 
     * @param clienteId  El ID del cliente.
     * @param nombre     El nuevo nombre del cliente.
     * @param email      El nuevo email del cliente.
     * @param plan       El nuevo plan del cliente.
     * @param particular Indica si el cliente es particular o no.
     * @return El cliente actualizado.
     * 
     * @throws RuntimeException Si no se encuentra el cliente con el ID dado.
     * 
     * @author Dante Zulli
     */
    public ClienteDTO actualizarCliente(int clienteId, String nombre, String email, String plan, boolean particular) {
        Cliente cliente = (Cliente) clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPlan(plan);
        cliente.setParticular(particular);

        clienteRepository.update(cliente);

        return new ClienteDTO(cliente);
    }

    /**
     * Asocia una dirección a un cliente dado su ID.
     * 
     * @param clienteId   El ID del cliente.
     * @param direccionId El ID de la dirección.
     * @return El cliente actualizado con la dirección asociada.
     * 
     * @throws RuntimeException Si no se encuentra el cliente con el ID dado o si
     *                          no se encuentra la dirección con el ID dado.
     * 
     * @author Dante Zulli
     */
    public ClienteDTO asociarDireccion(int clienteId, int direccionId) {
        Cliente cliente = (Cliente) clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        Direccion direccion = (Direccion) direccionRepository.findById(direccionId)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + direccionId));

        cliente.setDireccion(direccion);

        clienteRepository.update(cliente);

        return new ClienteDTO(cliente);
    }

    /**
     * Crea un nuevo técnico en la base de datos.
     * 
     * @param nombre      El nombre del técnico.
     * @param email       El email del técnico.
     * @param nroContacto El número de contacto del técnico.
     * @param empresa     La empresa del técnico.
     * @param rolId       El ID del rol del técnico.
     * @return El técnico creado.
     * 
     * @author Dante Zulli
     */
    public TecnicoDTO crearTecnico(String nombre, String email, String nroContacto, String empresa, int rolId) {
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(nombre);
        tecnico.setEmail(email);
        tecnico.setNroContacto(nroContacto);
        tecnico.setEmpresa(empresa);

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
        tecnico.setRol(rol);

        return new TecnicoDTO(tecnicoRepository.save(tecnico));
    }

    /**
     * Actualiza los datos de un técnico dado su ID.
     * 
     * @param tecnicoId   El ID del técnico.
     * @param nombre      El nuevo nombre del técnico.
     * @param email       El nuevo email del técnico.
     * @param nroContacto El nuevo número de contacto del técnico.
     * @param empresa     La nueva empresa del técnico.
     * @param rolId       El nuevo ID del rol del técnico.
     * @return El técnico actualizado.
     * 
     * @throws RuntimeException Si no se encuentra el técnico con el ID dado o si
     *                          no se encuentra el rol con el ID dado.
     * 
     * @author Dante Zulli
     */
    public TecnicoDTO actualizarTecnico(int tecnicoId, String nombre, String email, String nroContacto, String empresa,
            int rolId) {
        Tecnico tecnico = (Tecnico) tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado con ID: " + tecnicoId));

        tecnico.setNombre(nombre);
        tecnico.setEmail(email);
        tecnico.setNroContacto(nroContacto);
        tecnico.setEmpresa(empresa);

        if (rolId > 0) {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
            tecnico.setRol(rol);
        }

        tecnicoRepository.update(tecnico);
        return new TecnicoDTO(tecnico);
    }
}