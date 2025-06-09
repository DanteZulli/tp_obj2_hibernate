package com.grupo25.tp_obj2_hibernate.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.repository.AreaRepository;
import com.grupo25.tp_obj2_hibernate.repository.ClienteRepository;
import com.grupo25.tp_obj2_hibernate.repository.DireccionRepository;
import com.grupo25.tp_obj2_hibernate.repository.TecnicoRepository;
import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;
import com.grupo25.tp_obj2_hibernate.model.entities.Area;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * usuarios.
 * 
 * @author Grupo 25
 */
@Service
public class UsuarioService {

    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final DireccionRepository direccionRepository;
    private final AreaRepository areaRepository;

    public UsuarioService(
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            DireccionRepository direccionRepository,
            AreaRepository areaRepository) {
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.direccionRepository = direccionRepository;
        this.areaRepository = areaRepository;
    }
    
    
    /**
     * Trae lista de clientes de la base de datos.
     * 
     * @return lista de clientes.
     * 
     * @author Ignacio Cruz
     */
    public List<Cliente> findAllClientes(){
      return clienteRepository.findAll();
    }

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
    public Cliente crearCliente(String nombre, String email, String plan, boolean particular) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPlan(plan);
        cliente.setParticular(particular);
        return clienteRepository.save(cliente);
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
    public Cliente actualizarCliente(int clienteId, String nombre, String email, String plan, boolean particular) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPlan(plan);
        cliente.setParticular(particular);

        return clienteRepository.save(cliente);

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
    public Cliente asociarDireccion(int clienteId, int direccionId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        Direccion direccion = direccionRepository.findById(direccionId)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + direccionId));

        cliente.setDireccion(direccion);

        return clienteRepository.save(cliente);

    }

    /**
     * Crea un nuevo técnico en la base de datos.
     * 
     * @param nombre      El nombre del técnico.
     * @param email       El email del técnico.
     * @param nroContacto El número de contacto del técnico.
     * @param empresa     La empresa del técnico.
     * @param areaId      El ID del área del técnico.
     * @return El técnico creado.
     * 
     * @author Dante Zulli
     */
    public Tecnico crearTecnico(String nombre, String email, String nroContacto, String empresa, int areaId) {
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(nombre);
        tecnico.setEmail(email);
        tecnico.setNroContacto(nroContacto);
        tecnico.setEmpresa(empresa);

        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));
        tecnico.setArea(area);

        return tecnicoRepository.save(tecnico);
    }

    /**
     * Actualiza los datos de un técnico dado su ID.
     * 
     * @param tecnicoId   El ID del técnico.
     * @param nombre      El nuevo nombre del técnico.
     * @param email       El nuevo email del técnico.
     * @param nroContacto El nuevo número de contacto del técnico.
     * @param empresa     La nueva empresa del técnico.
     * @param areaId      El nuevo ID del área del técnico.
     * @return El técnico actualizado.
     * 
     * @throws RuntimeException Si no se encuentra el técnico con el ID dado o si
     *                          no se encuentra el área con el ID dado.
     * 
     * @author Dante Zulli
     */
    public Tecnico actualizarTecnico(int tecnicoId, String nombre, String email, String nroContacto, String empresa,
            int areaId) {
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado con ID: " + tecnicoId));

        tecnico.setNombre(nombre);
        tecnico.setEmail(email);
        tecnico.setNroContacto(nroContacto);
        tecnico.setEmpresa(empresa);

        if (areaId > 0) {
            Area area = areaRepository.findById(areaId)
                    .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));
            tecnico.setArea(area);
        }

        return tecnicoRepository.save(tecnico);
    }
}