package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.grupo25.tp_obj2_hibernate.service.TicketService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;
import com.grupo25.tp_obj2_hibernate.service.EtiquetaService;

@Controller
@RequestMapping("/tecnico/tickets")
@RequiredArgsConstructor
public class TicketsTecnicoViewController {

    private static final String TICKETS_VIEW = "tickets";
    private static final String TICKET_DETALLES_VIEW = "ticket-detalles";
    private static final String EDITAR_TICKET_VIEW = "editar-ticket";

    private final TicketService ticketService;
    private final CategoriaService categoriaService;
    private final EtiquetaService etiquetaService;

    @GetMapping
    public ModelAndView listarTickets(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String success) {
        ModelAndView mav = new ModelAndView(TICKETS_VIEW);
        mav.addObject("tickets", ticketService.obtenerTodosLosTickets());
        if (success != null) {
            mav.addObject("success", success);
        }
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView verDetallesTicket(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(TICKET_DETALLES_VIEW);
        mav.addObject("ticket", ticketService.obtenerTicket(id));
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarTicket(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(EDITAR_TICKET_VIEW);
        mav.addObject("ticket", ticketService.obtenerTicket(id));
        mav.addObject("categorias", categoriaService.obtenerCategorias());
        mav.addObject("etiquetas", etiquetaService.obtenerEtiquetas());
        return mav;
    }
}