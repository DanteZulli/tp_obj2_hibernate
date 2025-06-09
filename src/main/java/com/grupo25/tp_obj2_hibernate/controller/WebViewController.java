package com.grupo25.tp_obj2_hibernate.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.service.UsuarioService;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;
import com.grupo25.tp_obj2_hibernate.service.ComentarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebViewController {

    private final UsuarioService usuarioService;

    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaService categoriaService;
    private final ComentarioService comentarioService;

    public WebViewController(TicketService ticketService, UsuarioRepository usuarioRepository, CategoriaService categoriaService, 
    		UsuarioService usuarioService,
    		ComentarioService comentarioService) {
        this.ticketService = ticketService;
        this.usuarioRepository = usuarioRepository;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.comentarioService = comentarioService;
    }

    @GetMapping("/tickets")
    public ModelAndView mostrarTodosLosTickets() {
        log.info("📝 Logging: Mostrando página de todos los tickets");
        ModelAndView mav = new ModelAndView("tickets");
        
        try {
            mav.addObject("tickets", ticketService.getTickets());
        } catch (Exception e) {
            log.error("📝 Logging: Error al obtener los tickets", e);
            mav.addObject("error", "Error al cargar los tickets");
            mav.addObject("tickets", new java.util.ArrayList<>()); // Lista vacía para evitar null
        }
        
        return mav;
    }

    @GetMapping("/tickets/crear")
    public ModelAndView mostrarFormularioCrearTicket() {
        log.info("📝 Logging: Mostrando formulario para crear ticket");
        ModelAndView mav = new ModelAndView("crear-ticket");
        
        try {
            mav.addObject("categorias", categoriaService.getCategorias());
        } catch (Exception e) {
            log.error("📝 Logging: Error al obtener las categorías", e);
            mav.addObject("error", "Error al cargar las categorías");
        }
        
        return mav;
    }

    @GetMapping("/tickets/asignados")
    public ModelAndView mostrarTicketsAsignados() {
        log.info("📝 Logging: Mostrando tickets asignados al usuario actual");
        ModelAndView mav = new ModelAndView("tickets-asignados");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        try {
            Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Para técnicos, mostrar tickets asignados a ellos
            // Para clientes, mostrar tickets creados por ellos
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TECNICO"))) {
                log.info("📝 Logging: Obteniendo tickets asignados al técnico '{}'", usuario.getNombre());
                mav.addObject("tickets", ticketService.getTodosLosTicketsPorUsuarioAsignado(usuario.getId()));
                mav.addObject("tipoVista", "asignados");
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
                log.info("📝 Logging: Obteniendo tickets creados por el cliente '{}'", usuario.getNombre());
                mav.addObject("tickets", ticketService.getTodosLosTicketsPorUsuarioCreador(usuario.getId()));
                mav.addObject("tipoVista", "creados");
            } else {
                // Admin puede ver todos
                log.info("📝 Logging: Administrador '{}' viendo todos los tickets", usuario.getNombre());
                mav.addObject("tickets", ticketService.getTickets());
                mav.addObject("tipoVista", "todos");
            }
        } catch (Exception e) {
            log.error("📝 Logging: Error al obtener los tickets asignados", e);
            mav.addObject("error", "Error al cargar los tickets");
            mav.addObject("tickets", new java.util.ArrayList<>()); // Lista vacía para evitar null
            mav.addObject("tipoVista", "error");
        }
        
        return mav;
    }

    @GetMapping("/clientes")
    public ModelAndView mostrarClientes() {
        log.info("📝 Logging: Mostrando página de clientes");
        List<Cliente> clientesLista = usuarioService.findAllClientes();
        ModelAndView mav = new ModelAndView("clientes");
        mav.addObject("clientes", clientesLista);
        return mav;
    }

    
	/**
	 * Redirige a informacion de usuario
	 * 
	 * @author Ignacio Cruz
	 */
    @GetMapping("/profile")
    public ModelAndView mostrarProfile() {
        log.info("📝 Logging: Mostrando página de informacion de usuario");
        ModelAndView mav = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        try {
        	Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                mav.addObject("usuario", usuario);
        } catch (Exception e) {
            log.error("📝 Logging: Error al obtener usuario", e);
            mav.addObject("error", "Error al cargar usuario");
        }
        
        return mav;
    }

    @GetMapping("/tickets/{id}")
    public ModelAndView mostrarDetallesTicket(@PathVariable int id) {
        log.info("📝 Logging: Mostrando detalles del ticket {}", id);
        ModelAndView mav = new ModelAndView("ticket-detalles");
        
        try {
            Ticket ticket = ticketService.getTicketPorId(id);
            mav.addObject("ticket", ticket);
            mav.addObject("comentarios", comentarioService.getTodosLosComentariosPorTicket(id));
        } catch (Exception e) {
            log.error("📝 Logging: Error al obtener los detalles del ticket", e);
            mav.addObject("error", "Error al cargar los detalles del ticket");
        }
        
        return mav;
    }

    @GetMapping("/tickets/editar/{id}")
    public ModelAndView mostrarFormularioEditarTicket(@PathVariable int id) {
        log.info("📝 Logging: Mostrando formulario para editar el ticket {}", id);
        ModelAndView mav = new ModelAndView("editar-ticket");
        
        try {
            Ticket ticket = ticketService.getTicketPorId(id);
            mav.addObject("ticket", ticket);
            mav.addObject("categorias", categoriaService.getCategorias());
        } catch (Exception e) {
            log.error("📝 Logging: Error al obtener los datos para editar el ticket", e);
            mav.addObject("error", "Error al cargar los datos para editar el ticket");
        }
        
        return mav;
    }

    @PostMapping("/tickets/editar/{id}")
    public String procesarEdicionTicket(@PathVariable int id, 
                                       @RequestParam String titulo, 
                                       @RequestParam String descripcion,
                                       @RequestParam String estado,
                                       @RequestParam String prioridad,
                                       @RequestParam int categoriaId,
                                       RedirectAttributes redirectAttributes) {
        log.info("📝 Logging: Procesando edición del ticket {}", id);
        
        try {
            Ticket ticket = ticketService.getTicketPorId(id);
            ticket.setTitulo(titulo);
            ticket.setDescripcion(descripcion);
            ticket.setEstado(estado);
            ticket.setPrioridad(prioridad);
            ticket.setCategoria(categoriaService.getCategoriaPorId(categoriaId));
            
            ticketService.actualizarTicket(ticket);
            redirectAttributes.addFlashAttribute("success", "Ticket actualizado correctamente");
            return "redirect:/tickets";
        } catch (Exception e) {
            log.error("📝 Logging: Error al procesar la edición del ticket", e);
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el ticket");
            return "redirect:/tickets/editar/" + id;
        }
    }

    @PostMapping("/tickets/crear")
    public String procesarCreacionTicket(@RequestParam String titulo, 
                                        @RequestParam String descripcion, 
                                        @RequestParam String prioridad,
                                        @RequestParam int categoriaId, 
                                        RedirectAttributes redirectAttributes) {
        log.info("📝 Logging: Procesando creación del ticket");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        try {
            Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            ticketService.crearTicketCompleto(titulo, descripcion, "ABIERTO", prioridad, usuario.getId(), categoriaId);
            redirectAttributes.addFlashAttribute("success", "Ticket creado correctamente");
            return "redirect:/tickets";
        } catch (Exception e) {
            log.error("📝 Logging: Error al procesar la creación del ticket", e);
            redirectAttributes.addFlashAttribute("error", "Error al crear el ticket");
            return "redirect:/tickets/crear";
        }
    }
} 