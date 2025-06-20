package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.service.TicketService;

@Controller
public class IndexViewController {

    private static final String INDEX_VIEW = "index";

    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;

    public IndexViewController(TicketService ticketService, UsuarioRepository usuarioRepository) {
        this.ticketService = ticketService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/")
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            mav.addObject("tipoUsuario", "Administrador");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstado("ABIERTO"));
            mav.addObject("ticketsEnProgreso", ticketService.contarTicketsPorEstado("EN_PROGRESO"));
            mav.addObject("ticketsResueltos", ticketService.contarTicketsPorEstado("RESUELTO"));
            mav.addObject("ticketsUrgentes", ticketService.contarTicketsPorPrioridad("ALTA"));

            mav.addObject("tickets", ticketService.obtenerTodosLosTickets());
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TECNICO"))) {
            mav.addObject("tipoUsuario", "Técnico");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstado("ABIERTO"));
            mav.addObject("ticketsEnProgreso", ticketService.contarTicketsPorEstado("EN_PROGRESO"));
            mav.addObject("ticketsResueltos", ticketService.contarTicketsPorEstado("RESUELTO"));
            mav.addObject("ticketsUrgentes", ticketService.contarTicketsPorPrioridad("ALTA"));

            mav.addObject("tickets", ticketService.obtenerTodosLosTickets());
        } else {
            Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            mav.addObject("tipoUsuario", "Cliente");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstadoYCreador("ABIERTO", usuario.getId()));
            mav.addObject("ticketsEnProgreso",
                    ticketService.contarTicketsPorEstadoYCreador("EN_PROGRESO", usuario.getId()));
            mav.addObject("ticketsResueltos",
                    ticketService.contarTicketsPorEstadoYCreador("RESUELTO", usuario.getId()));
            mav.addObject("ticketsUrgentes", ticketService.contarTicketsPorPrioridadYCreador("ALTA", usuario.getId()));

            mav.addObject("tickets", ticketService.obtenerTodosLosTicketsPorUsuarioCreador(usuario.getId()));
        }

        return mav;
    }
    
    @GetMapping("/{estado}")
    public ModelAndView getIndexEstado(@PathVariable String estado) {
        ModelAndView mav = new ModelAndView(INDEX_VIEW);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            mav.addObject("tipoUsuario", "Administrador");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstado("ABIERTO"));
            mav.addObject("ticketsEnProgreso", ticketService.contarTicketsPorEstado("EN_PROGRESO"));
            mav.addObject("ticketsResueltos", ticketService.contarTicketsPorEstado("RESUELTO"));
            mav.addObject("ticketsUrgentes", ticketService.contarTicketsPorPrioridad("ALTA"));

            if (estado.equalsIgnoreCase("ALTA")) {
                mav.addObject("tickets", ticketService.obtenerTicketsPorPrioridad("ALTA"));
            } else {
                mav.addObject("tickets", ticketService.obtenerEstadoTicketIgnoreCase(estado));
            }
            
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TECNICO"))) {
            mav.addObject("tipoUsuario", "Técnico");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstado("ABIERTO"));
            mav.addObject("ticketsEnProgreso", ticketService.contarTicketsPorEstado("EN_PROGRESO"));
            mav.addObject("ticketsResueltos", ticketService.contarTicketsPorEstado("RESUELTO"));
            mav.addObject("ticketsUrgentes", ticketService.contarTicketsPorPrioridad("ALTA"));
            
            
            
            if (estado.equalsIgnoreCase("ALTA")) {
                mav.addObject("tickets", ticketService.obtenerTicketsPorPrioridad("ALTA"));
            } else {
                mav.addObject("tickets", ticketService.obtenerEstadoTicketIgnoreCase(estado));
            }
        } else {
            Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            mav.addObject("tipoUsuario", "Cliente");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstadoYCreador("ABIERTO", usuario.getId()));
            mav.addObject("ticketsEnProgreso",
                    ticketService.contarTicketsPorEstadoYCreador("EN_PROGRESO", usuario.getId()));
            mav.addObject("ticketsResueltos",
                    ticketService.contarTicketsPorEstadoYCreador("RESUELTO", usuario.getId()));
            mav.addObject("ticketsUrgentes", ticketService.contarTicketsPorPrioridadYCreador("ALTA", usuario.getId()));

            if (estado.equalsIgnoreCase("ALTA")) {
                mav.addObject("tickets", ticketService.obtenerTicketsPorPrioridadYCreador("ALTA", usuario.getId()));
            } else {
                mav.addObject("tickets", ticketService.obtenerEstadoTicketPorUsuario(estado, usuario.getId()));
            }

        }

        return mav;
    }
}
