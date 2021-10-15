package com.egg.programacion.libreriaspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @author Lucía
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tittle", "Proyecto Egg Librería");
        return "index.html";
    }

}
