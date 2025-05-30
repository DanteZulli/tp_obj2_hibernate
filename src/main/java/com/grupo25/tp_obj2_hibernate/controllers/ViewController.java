package com.grupo25.tp_obj2_hibernate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    private static final String INDEX_VIEW = "index";

    @GetMapping("/")
    public String getIndex() {
        return INDEX_VIEW;
    }
    

}
