package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;
import com.grupo25.tp_obj2_hibernate.service.TicketService;

@Controller
public class TicketsViewController {

    private static final String TICKETS_VIEW = "tickets";
    private static final String CREAR_TICKET_VIEW = "crear-ticket";
    private static final String TICKETS_ASIGNADOS_VIEW = "tickets-asignados";
    private static final String TICKET_DETALLES_VIEW = "ticket-detalles";
    private static final String EDITAR_TICKET_VIEW = "editar-ticket";

    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaService categoriaService;

    public TicketsViewController(TicketService ticketService, UsuarioRepository usuarioRepository,
            CategoriaService categoriaService) {
        this.ticketService = ticketService;
        this.usuarioRepository = usuarioRepository;
        this.categoriaService = categoriaService;
    }


    @GetMapping("/crear")
    public ModelAndView getCrearTicket() {
        ModelAndView mav = new ModelAndView(CREAR_TICKET_VIEW);
        mav.addObject("categorias", categoriaService.getCategorias());
        return mav;
    }

    @GetMapping("/asignados")
    public ModelAndView getTicketsAsignados() {
        ModelAndView mav = new ModelAndView(TICKETS_ASIGNADOS_VIEW);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TECNICO"))) {
            mav.addObject("tickets", ticketService.getTodosLosTicketsPorUsuarioAsignado(usuario.getId()));
            mav.addObject("tipoVista", "asignados");
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            mav.addObject("tickets", ticketService.getTodosLosTicketsPorUsuarioCreador(usuario.getId()));
            mav.addObject("tipoVista", "creados");
        } else {
            mav.addObject("tickets", ticketService.getAllTickets());
            mav.addObject("tipoVista", "todos");
        }

        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getDetallesTicket(@PathVariable int id) {
        ModelAndView mav = new ModelAndView(TICKET_DETALLES_VIEW);
        Ticket ticket = ticketService.getTicketPorId(id);
        mav.addObject("ticket", ticket);
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView getEditarTicket(@PathVariable int id) {
        ModelAndView mav = new ModelAndView(EDITAR_TICKET_VIEW);
        Ticket ticket = ticketService.getTicketPorId(id);
        mav.addObject("ticket", ticket);
        return mav;
    }

}
