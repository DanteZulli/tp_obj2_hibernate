package com.grupo25.tp_obj2_hibernate.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grupo25.tp_obj2_hibernate.service.TicketService;

@Controller
public class ViewController {

    private static final String INDEX_VIEW = "index";

    private final TicketService ticketService;

    public ViewController(TicketService ticketService) {
        this.ticketService = ticketService;
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
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TECNICO"))) {
            mav.addObject("tipoUsuario", "Técnico");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstado("ABIERTO"));
            mav.addObject("ticketsEnProgreso", ticketService.contarTicketsPorEstado("EN_PROGRESO"));
            mav.addObject("ticketsResueltos", ticketService.contarTicketsPorEstado("RESUELTO"));
        } else {
            mav.addObject("tipoUsuario", "Cliente");
            mav.addObject("ticketsAbiertos", ticketService.contarTicketsPorEstadoYCreador("ABIERTO", Integer.parseInt(auth.getName())));
            mav.addObject("ticketsEnProgreso", ticketService.contarTicketsPorEstadoYCreador("EN_PROGRESO", Integer.parseInt(auth.getName())));
            mav.addObject("ticketsResueltos", ticketService.contarTicketsPorEstadoYCreador("RESUELTO", Integer.parseInt(auth.getName())));
        }

        return mav;
    }
}
