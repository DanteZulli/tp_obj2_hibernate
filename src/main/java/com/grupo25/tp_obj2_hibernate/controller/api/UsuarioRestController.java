package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con los usuarios.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private UsuarioService usuarioService;

    public UsuarioRestController(@Autowired UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Crea un nuevo cliente.
     * 
     * @param nombre     El nombre del cliente
     * @param email      El email del cliente
     * @param plan       El plan del cliente
     * @param particular Indica si el cliente es particular o no
     * @return El cliente creado
     * 
     * @author Dante Zulli
     */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> crearCliente(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String plan,
            @RequestParam boolean particular) {
        Cliente cliente = usuarioService.crearCliente(nombre, email, plan, particular);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Actualiza un cliente existente.
     * 
     * @param clienteId  El ID del cliente a actualizar
     * @param nombre     El nuevo nombre del cliente
     * @param email      El nuevo email del cliente
     * @param plan       El nuevo plan del cliente
     * @param particular Indica si el cliente es particular o no
     * @return El cliente actualizado
     * 
     * @throws RuntimeException si no se encuentra el cliente con el ID dado
     * 
     * @author Dante Zulli
     */
    @PutMapping("/clientes/{clienteId}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable int clienteId,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String plan,
            @RequestParam boolean particular) {
        try {
            Cliente cliente = usuarioService.actualizarCliente(clienteId, nombre, email, plan, particular);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            log.error("Error al actualizar el cliente con ID: {}", clienteId, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Asocia una dirección a un cliente.
     * 
     * @param clienteId   El ID del cliente
     * @param direccionId El ID de la dirección a asociar
     * @return El cliente con la dirección asociada
     * 
     * @throws RuntimeException si no se encuentra el cliente con el ID dado o si
     *                          no se encuentra la dirección con el ID dado
     * 
     * @author Dante Zulli
     */
    @PostMapping("/clientes/{clienteId}/direccion")
    public ResponseEntity<Cliente> asociarDireccion(
            @PathVariable int clienteId,
            @RequestParam int direccionId) {
        try {
            Cliente cliente = usuarioService.asociarDireccion(clienteId, direccionId);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            log.error("Error al asociar la dirección al cliente con ID: {}", clienteId, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo técnico.
     * 
     * @param nombre      El nombre del técnico
     * @param email       El email del técnico
     * @param nroContacto El número de contacto del técnico
     * @param empresa     La empresa del técnico
     * @param areaId      El ID del área del técnico
     * @return El técnico creado
     * 
     * @author Dante Zulli
     */
    @PostMapping("/tecnicos")
    public ResponseEntity<Tecnico> crearTecnico(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String nroContacto,
            @RequestParam String empresa,
            @RequestParam int areaId) {
        try {
            Tecnico tecnico = usuarioService.crearTecnico(nombre, email, nroContacto, empresa, areaId);
            return ResponseEntity.ok(tecnico);
        } catch (RuntimeException e) {
            log.error("Error al crear el técnico", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un técnico existente.
     * 
     * @param tecnicoId   El ID del técnico a actualizar
     * @param nombre      El nuevo nombre del técnico
     * @param email       El nuevo email del técnico
     * @param nroContacto El nuevo número de contacto del técnico
     * @param empresa     La nueva empresa del técnico
     * @param areaId      El nuevo ID del área del técnico
     * @return El técnico actualizado
     * 
     * @throws RuntimeException si no se encuentra el técnico con el ID dado
     * 
     * @author Dante Zulli
     */
    @PutMapping("/tecnicos/{tecnicoId}")
    public ResponseEntity<Tecnico> actualizarTecnico(
            @PathVariable int tecnicoId,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String nroContacto,
            @RequestParam String empresa,
            @RequestParam int areaId) {
        try {
            Tecnico tecnico = usuarioService.actualizarTecnico(tecnicoId, nombre, email, nroContacto, empresa, areaId);
            return ResponseEntity.ok(tecnico);
        } catch (RuntimeException e) {
            log.error("Error al actualizar el técnico con ID: {}", tecnicoId, e);
            return ResponseEntity.notFound().build();
        }
    }
}