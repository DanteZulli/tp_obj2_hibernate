package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;
import com.grupo25.tp_obj2_hibernate.service.TicketService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/cliente/tickets")
public class TicketsClienteViewController {

    private static final String TICKETS_VIEW = "tickets";
    private static final String CREAR_TICKET_VIEW = "crear-ticket";
    private static final String TICKET_DETALLES_VIEW = "ticket-detalles";

    private final CategoriaService categoriaService;
    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;

    public TicketsClienteViewController(CategoriaService categoriaService, TicketService ticketService, UsuarioRepository usuarioRepository) {
        this.categoriaService = categoriaService;
        this.ticketService = ticketService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ModelAndView listarTickets(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam(required = false) String success) {
        ModelAndView mav = new ModelAndView(TICKETS_VIEW);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        mav.addObject("tickets", ticketService.getTodosLosTicketsPorUsuarioCreador(usuario.getId()));
        if (success != null) {
            mav.addObject("success", success);
        }
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView mostrarFormularioCreacion() {
        ModelAndView mav = new ModelAndView(CREAR_TICKET_VIEW);
        mav.addObject("categorias", categoriaService.getCategorias());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView verDetallesTicket(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(TICKET_DETALLES_VIEW);
        mav.addObject("ticket", ticketService.getTicketPorId(id));
        return mav;
    }
}
