package com.grupo25.tp_obj2_hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.repository.AreaRepository;
import com.grupo25.tp_obj2_hibernate.repository.ClienteRepository;
import com.grupo25.tp_obj2_hibernate.repository.DireccionRepository;
import com.grupo25.tp_obj2_hibernate.repository.TecnicoRepository;
import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;
import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final DireccionRepository direccionRepository;
    private final AreaRepository areaRepository;
    private final UsuarioRepository usuarioRepository;
    
    public List<Cliente> findAllClientes(){
      return clienteRepository.findAll();
    }

    public Cliente crearCliente(String nombre, String email, String plan, boolean particular) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPlan(plan);
        cliente.setParticular(particular);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(int clienteId, String nombre, String email, String plan, boolean particular) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPlan(plan);
        cliente.setParticular(particular);

        return clienteRepository.save(cliente);
    }

    public Cliente asociarDireccion(int clienteId, int direccionId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        Direccion direccion = direccionRepository.findById(direccionId)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + direccionId));

        cliente.setDireccion(direccion);

        return clienteRepository.save(cliente);
    }

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

    public Usuario obtenerUsuario(int usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
    }
}