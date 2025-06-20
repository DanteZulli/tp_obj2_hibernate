package com.grupo25.tp_obj2_hibernate.controller.view;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.repository.CategoriaRepository;
import com.grupo25.tp_obj2_hibernate.repository.TecnicoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ConsultaTicketsViewController {

    private static final String CONSULTA_TICKETS_VIEW = "consulta-tickets";
    private final CategoriaRepository categoriaRepository;
    private final TecnicoRepository tecnicoRepository;

    @GetMapping("/consultar")
    public ModelAndView getConsultaTickets() {
        ModelAndView mav = new ModelAndView(CONSULTA_TICKETS_VIEW);

        List<Categoria> categorias = categoriaRepository.findAll();
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        mav.addObject("categorias", categorias);
        mav.addObject("tecnicos", tecnicos);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            mav.addObject("userRoles", roles);
        }

        return mav;
    }

}
