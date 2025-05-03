package com.grupo25.tp_obj2_hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con los usuarios.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario.
     * 
     * @param nombre El nombre del usuario
     * @param email  El email del usuario
     * @return El usuario creado
     * 
     * @author Dante Zulli
     */
    @PutMapping("/{usuarioId}/datos-personales")
    public ResponseEntity<Usuario> actualizarDatosPersonales(
            @PathVariable int usuarioId,
            @RequestParam String nombre,
            @RequestParam String email) {
        try {
            Usuario usuario = usuarioService.actualizarDatosPersonales(usuarioId, nombre, email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            log.error("Error al actualizar los datos personales del usuario con ID: {}", usuarioId, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo cliente.
     * 
     * @param nombre      El nombre del cliente
     * @param email       El email del cliente
     * @param plan        El plan del cliente
     * @param particular  Indica si el cliente es particular o no
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
     * @param clienteId   El ID del cliente a actualizar
     * @param nombre      El nuevo nombre del cliente
     * @param email       El nuevo email del cliente
     * @param plan        El nuevo plan del cliente
     * @param particular  Indica si el cliente es particular o no
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
     * @param clienteId  El ID del cliente
     * @param direccion  La dirección a asociar
     * @param esFiscal   Indica si la dirección es fiscal o no
     * @return El cliente con la dirección asociada
     * 
     * @throws RuntimeException si no se encuentra el cliente con el ID dado
     * 
     * @author Dante Zulli
     */
    @PostMapping("/clientes/{clienteId}/direccion")
    public ResponseEntity<Cliente> asociarDireccion(
            @PathVariable int clienteId,
            @RequestBody Direccion direccion,
            @RequestParam boolean esFiscal) {
        try {
            Cliente cliente = usuarioService.asociarDireccion(clienteId, direccion, esFiscal);
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
     * @param rolId       El ID del rol del técnico
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
            @RequestParam int rolId) {
        try {
            Tecnico tecnico = usuarioService.crearTecnico(nombre, email, nroContacto, empresa, rolId);
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
     * @param rolId       El nuevo ID del rol del técnico
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
            @RequestParam int rolId) {
        try {
            Tecnico tecnico = usuarioService.actualizarTecnico(tecnicoId, nombre, email, nroContacto, empresa, rolId);
            return ResponseEntity.ok(tecnico);
        } catch (RuntimeException e) {
            log.error("Error al actualizar el técnico con ID: {}", tecnicoId, e);
            return ResponseEntity.notFound().build();
        }
    }
}