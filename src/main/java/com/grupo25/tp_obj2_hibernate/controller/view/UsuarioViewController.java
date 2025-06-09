package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;

@Controller
public class UsuarioViewController {

    private static final String USUARIO_VIEW = "usuario";

    private final UsuarioRepository usuarioRepository;

    public UsuarioViewController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/perfil")
    public ModelAndView getPerfil() {
        ModelAndView mav = new ModelAndView(USUARIO_VIEW);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByNombreUsuario(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (usuario instanceof Tecnico) {
            mav.addObject("tipoUsuario", "Técnico");
            mav.addObject("usuario", (Tecnico) usuario);
        } else if (usuario instanceof Cliente) {
            mav.addObject("tipoUsuario", "Cliente");
            mav.addObject("usuario", (Cliente) usuario);
        } else {
            mav.addObject("tipoUsuario", "Usuario");
            mav.addObject("usuario", usuario);
        }
        
        return mav;
    }
}
