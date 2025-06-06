package com.grupo25.tp_obj2_hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ViewController {

    private static final String INDEX_VIEW = "index";

    @GetMapping("/")
    public ModelAndView getIndex() {
        return new ModelAndView(INDEX_VIEW);
    }
    

}
