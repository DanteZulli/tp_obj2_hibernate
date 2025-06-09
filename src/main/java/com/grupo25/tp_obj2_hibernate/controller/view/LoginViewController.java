package com.grupo25.tp_obj2_hibernate.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión exitosamente");
        }
        return "login";
    }
} 