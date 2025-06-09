package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;

@Controller
@RequestMapping("/tecnico/tickets")
public class TicketsTecnicoViewController {

    private static final String TICKETS_VIEW = "tickets";
    private static final String TICKET_DETALLES_VIEW = "ticket-detalles";
    private static final String EDITAR_TICKET_VIEW = "editar-ticket";

    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaService categoriaService;

    public TicketsTecnicoViewController(TicketService ticketService,
            UsuarioRepository usuarioRepository,
            CategoriaService categoriaService) {
        this.ticketService = ticketService;
        this.usuarioRepository = usuarioRepository;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ModelAndView listarTickets(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String success) {
        ModelAndView mav = new ModelAndView(TICKETS_VIEW);
        mav.addObject("tickets", ticketService.getAllTickets());
        if (success != null) {
            mav.addObject("success", success);
        }
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView verDetallesTicket(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(TICKET_DETALLES_VIEW);
        mav.addObject("ticket", ticketService.getTicketPorId(id));
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarTicket(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(EDITAR_TICKET_VIEW);
        mav.addObject("ticket", ticketService.getTicketPorId(id));
        mav.addObject("categorias", categoriaService.getCategorias());
        return mav;
    }
}